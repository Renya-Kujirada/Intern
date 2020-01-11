package jp.co.nssol.sysrdc.santa.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerSample {

    public static void main(String[] args) {

        // ロガー（slf4j.Logger）の取得.
        Logger logger = LoggerFactory.getLogger(LoggerSample.class);

        // infoログ出力
        logger.info("{}",    "HOGE");
        logger.info("{} {}", "HOGE", "FUGA");
        logger.info("{} {}", "HOGE");
        logger.info("{}",    "HOGE", "FUGA");
        logger.info("\\{}",  "HOGE");
        logger.info("{ }",   "HOGE");

        // debugログ出力
        logger.debug("{}",    "HOGE");

    }

}
