package edu.pw.hwmonitor;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class ConfigurationTest {

    private LocalDateTimeConverter  c = new LocalDateTimeConverter();

    @Test
    public void givenValidLdtShouldReturnCorrectTimestamp() throws Exception {
        LocalDateTime ldt;
        Timestamp ts;

        ldt = LocalDateTime.now();
        ts =  Timestamp.valueOf(ldt.withNano(0));

        assertThat(c.convertToDatabaseColumn(ldt)).isEqualTo(ts);
        assertThat(c.convertToDatabaseColumn(null)).isEqualTo(null);
    }

    @Test
    public void givenNotValidLdtShouldReturnNull() throws Exception {
        assertThat(c.convertToDatabaseColumn(null)).isEqualTo(null);
    }

    @Test
    public void givenValidTimestampShouldReturnLdt() throws Exception {
        LocalDateTime ldt = LocalDateTime.now();
        Timestamp ts =Timestamp.valueOf(ldt);
        assertThat(c.convertToEntityAttribute(ts)).isEqualTo(ldt);
    }
}
