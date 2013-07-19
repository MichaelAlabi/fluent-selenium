package org.seleniumhq.selenium.fluent.monitors;

import com.codahale.metrics.ConsoleReporter;
import org.junit.Test;
import org.seleniumhq.selenium.fluent.Monitor;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class CodehaleMonitorTest {

    @Test
    public void codehale_hooked_up_to_monitor() {

        CodehaleMonitor cm = new CodehaleMonitor();
        Monitor.Timer timer = cm.start("?.div().span()");
        try {
            Thread.sleep(999);
        } catch (InterruptedException e) {
        }
        timer.end();

        // Capture Stats ...

        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        final ConsoleReporter reporter = ConsoleReporter.forRegistry(cm.getMetrics())
                .convertRatesTo(TimeUnit.SECONDS)
                .convertDurationsTo(TimeUnit.SECONDS)
                .outputTo(new PrintStream(baos))
                .build();
        reporter.start(1, TimeUnit.SECONDS);
        try {
            Thread.sleep(1800);
        } catch (InterruptedException e) {
        }

        reporter.stop();

        // ... for assertion

        assertThat(baos.toString(), containsString(
                "-- Timers ----------------------------------------------------------------------\n" +
                "?.div().span()\n" +
                "             count = 1\n" +
                "         mean rate = 0.49 calls/second\n" +
                "     1-minute rate = 0.00 calls/second\n" +
                "     5-minute rate = 0.00 calls/second\n" +
                "    15-minute rate = 0.00 calls/second\n" +
                "               min = 1.00 seconds\n" +
                "               max = 1.00 seconds\n" +
                "              mean = 1.00 seconds\n" +
                "            stddev = 0.00 seconds\n" +
                "            median = 1.00 seconds\n" +
                "              75% <= 1.00 seconds\n" +
                "              95% <= 1.00 seconds\n" +
                "              98% <= 1.00 seconds\n" +
                "              99% <= 1.00 seconds\n" +
                "            99.9% <= 1.00 seconds"));

    }
}
