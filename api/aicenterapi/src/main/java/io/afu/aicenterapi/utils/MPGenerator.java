package io.afu.aicenterapi.utils;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MPGenerator {



    public static final String TYPE_MYSQL = "MYSQL";

    public static final String TYPE_ORACLE = "ORACLE";

    private String dbType = TYPE_MYSQL;

    private String outPut = "";

    private boolean enableLombok = false;

    private boolean enableSwagger = false;

    private String username = "";

    private String pwd = "";

    private String author = "";

    private String dbChartset = "utf8";

    private String pkg = "";

    private String dbDriver = "";

    private String dbHost = "127.0.0.1";

    private String dbPort = "";

    private String dbName = "";

    public String getDbType() {
        return dbType;
    }

    public MPGenerator setDbType(String dbType) {
        this.dbType = dbType;
        return this;
    }

    public String getOutPut() {
        return outPut;
    }

    public MPGenerator setOutPut(String outPut) {
        this.outPut = outPut;
        return this;
    }

    public boolean isEnableLombok() {
        return enableLombok;
    }

    public MPGenerator setEnableLombok(boolean enableLombok) {
        this.enableLombok = enableLombok;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public MPGenerator setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getPwd() {
        return pwd;
    }

    public MPGenerator setPwd(String pwd) {
        this.pwd = pwd;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public MPGenerator setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getDbChartset() {
        return dbChartset;
    }

    public MPGenerator setDbChartset(String dbChartset) {
        this.dbChartset = dbChartset;
        return this;
    }

    public String getPkg() {
        return pkg;
    }

    public MPGenerator setPkg(String pkg) {
        this.pkg = pkg;
        return this;
    }

    public String getDbDriver() {
        return dbDriver;
    }

    public MPGenerator setDbDriver(String dbDriver) {
        this.dbDriver = dbDriver;
        return this;
    }

    public String getDbHost() {
        return dbHost;
    }

    public MPGenerator setDbHost(String dbHost) {
        this.dbHost = dbHost;
        return this;
    }

    public String getDbPort() {
        return dbPort;
    }

    public MPGenerator setDbPort(String dbPort) {
        this.dbPort = dbPort;
        return this;
    }

    public String getDbName() {
        return dbName;
    }

    public MPGenerator setDbName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public MPGenerator enableSwagger() {
        this.enableSwagger = true;
        return this;
    }

    public static MPGenerator getInstance() {
        return new MPGenerator();
    }

    public MPGenerator useMySQL() {
        this.dbType = TYPE_MYSQL;
        this.dbDriver = "com.mysql.cj.jdbc.Driver";
        if (this.dbPort == null || this.dbPort.isEmpty()) {
            this.dbPort = "3306";
        }
        return this;
    }

    public MPGenerator useOracle() {
        this.dbType = TYPE_ORACLE;
        this.dbDriver = "oracle.jdbc.driver.OracleDriver";
        if (this.dbPort == null || this.dbPort.isEmpty()) {
            this.dbPort = "1521";
        }
        return this;
    }


    private String makeMySQLUrl() {
        return "jdbc:mysql://"+this.dbHost+":"+this.dbPort+"/"+this.dbName+"?useUnicode=true&characterEncoding="+this.dbChartset+"&useSSL=false";
    }


    private String makeOracleUrl() {
        return "jdbc:oracle:thin:@"+this.dbHost+":"+this.dbPort+":"+this.dbName;
    }


    public boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public void generator(String tables) {
        String url = "";
        if (this.dbType.equals(TYPE_MYSQL)) {
            url = makeMySQLUrl();
        } else if (this.dbType.equals(TYPE_ORACLE)) {
            url = makeOracleUrl();
        }
        FastAutoGenerator.create(url, getUsername(), getPwd())
                .globalConfig(builder -> {
                    if (enableSwagger) {
                        builder.enableSwagger();
                    }
                    if (isNotEmpty(author)) {
                        builder.author(author);
                    }
                    builder
                            .outputDir(outPut); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent(pkg) // 设置父包名
                            .entity("dao.entity") // 设置实体类包名
                            .mapper("dao.mapper") // 设置mapper包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, outPut)); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude(tables); // 设置需要生成的表名// 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }





    public static void main(String[] args) {
        MPGenerator.getInstance()
                .useMySQL()
                .setAuthor("rffanlab")
                .setUsername("aitaskservice")
                .setDbHost("sh-cdb-b9msxbfu.sql.tencentcdb.com")
                .setDbPort("63937")
                .setPwd("FHiu2h3f9u2h3f#")
                .setDbName("aitaskservice")
                .setOutPut("C:\\projects\\codeGen")
                .setPkg("io.afu.aitaskservice")
                .generator( "aitask");
    }



}
