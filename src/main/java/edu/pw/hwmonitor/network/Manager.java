package edu.pw.hwmonitor.network;

import edu.pw.hwmonitor.companies.CompanyRepository;
import edu.pw.hwmonitor.feeders.Feeder;
import edu.pw.hwmonitor.feeders.FeederRepository;
import edu.pw.hwmonitor.measurements.Measurement;
import edu.pw.hwmonitor.measurements.MeasurementRepository;
import edu.pw.hwmonitor.network.messages.Message;
import edu.pw.hwmonitor.network.messages.received.ChallengeReplyMessage;
import edu.pw.hwmonitor.network.messages.received.DataMessage;
import edu.pw.hwmonitor.network.messages.received.HelloMessage;
import edu.pw.hwmonitor.network.messages.sent.AccessDeniedMessage;
import edu.pw.hwmonitor.network.messages.sent.ConnectedMessage;
import edu.pw.hwmonitor.network.messages.sent.HelloChallengeMessage;
import edu.pw.hwmonitor.network.tcp.Connection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Optional;

@Component
public class Manager {

    private HashMap<Integer, Connection> connections;
    private CompanyRepository companyRepository;
    private FeederRepository feederRepository;
    private MeasurementRepository measurementRepository;

    @Autowired
    public Manager(CompanyRepository companyRepository, FeederRepository feederRepository, MeasurementRepository measurementRepository) {
        this.connections = new HashMap<>();
        this.companyRepository = companyRepository;
        this.feederRepository = feederRepository;
        this.measurementRepository = measurementRepository;
    }

    public synchronized void onMessageReceived(Connection connection, Message message) {
        try {
            switch (message.getType()) {
                case Hello:
                    handleMessage(connection, (HelloMessage) message);
                    break;
                case ChallengeReply:
                    handleMessage(connection, (ChallengeReplyMessage) message);
                    break;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public synchronized void onDataReceived(DataMessage message) {
        Optional.ofNullable(connections.get(message.getId())).ifPresent(connection -> {
            if (connection.isAuthenticated()) {
                Measurement measurement = new Measurement();
                measurement.setTime(message.getTimestamp());
                measurement.setFeederId(connection.getDatabseId());
                measurement.setValue(message.getTemperature());

                measurementRepository.save(measurement);
            }
        });
    }

    public synchronized void onDisconnected(Connection connection) {
        connection.disconnect();
        connections.remove(connection.getSessionId());
    }

    public void onConnected(Connection connection) {
        connections.put(connection.getSessionId(), connection);
    }

    private void handleMessage(Connection connection, HelloMessage message) {
        Optional<Integer> databaseId = companyRepository.findByNameEquals(message.getName())
                .flatMap(company -> feederRepository.findTopBySerialEqualsAndCompanyIdEquals(message.getId(), company.getId()))
                .map(Feeder::getId);

        if (databaseId.isPresent()) {
            connection.setDatabseId(databaseId.get());
            String challenge = Challenge.generate();
            connection.setAssignedChallenge(challenge);
            HelloChallengeMessage reply = new HelloChallengeMessage(connection.getSessionId(), challenge);
            connection.sendMessage(reply);
        }
        else {
            onDisconnected(connection);
        }
    }

    private void handleMessage(Connection connection, ChallengeReplyMessage message) throws NoSuchAlgorithmException {
        String secret = feederRepository.findTopByIdEquals(connection.getDatabseId())
                .map(Feeder::getSecret)
                .orElse("");

        if (Challenge.checkResponse(connection.getAssignedChallenge(), secret, message.getChallengeReply())) {
            connection.authenticate();
            connection.sendMessage(new ConnectedMessage());
        }
        else {
            connection.sendMessage(new AccessDeniedMessage());
            onDisconnected(connection);
        }
    }
}
