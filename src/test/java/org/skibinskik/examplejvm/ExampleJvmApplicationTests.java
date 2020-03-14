package org.skibinskik.examplejvm;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.core.util.StatusPrinter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ExampleJvmApplicationTests {

	@Test
	void contextLoads() {
		LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
		StatusPrinter.print(lc);
	}

	@Test
	void loggerTest() {
		log.info("test");
		log.warn("warn test");
		log.trace("trace test");
		log.error("error test");
		log.debug("debug test");
	}
}
