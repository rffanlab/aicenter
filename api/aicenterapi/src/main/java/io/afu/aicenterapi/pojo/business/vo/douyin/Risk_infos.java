/**
  * Copyright 2024 bejson.com 
  */
package io.afu.aicenterapi.pojo.business.vo.douyin;

/**
 * Auto-generated: 2024-03-31 16:45:27
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Risk_infos {

    private String content;
    private boolean risk_sink;
    private int type;
    private boolean vote;
    private boolean warn;
    public void setContent(String content) {
         this.content = content;
     }
     public String getContent() {
         return content;
     }

    public void setRisk_sink(boolean risk_sink) {
         this.risk_sink = risk_sink;
     }
     public boolean getRisk_sink() {
         return risk_sink;
     }

    public void setType(int type) {
         this.type = type;
     }
     public int getType() {
         return type;
     }

    public void setVote(boolean vote) {
         this.vote = vote;
     }
     public boolean getVote() {
         return vote;
     }

    public void setWarn(boolean warn) {
         this.warn = warn;
     }
     public boolean getWarn() {
         return warn;
     }

}