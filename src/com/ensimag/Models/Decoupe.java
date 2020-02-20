package com.ensimag.Models;

/**
 * Created by solokwal on 2/20/20.
 */
public class Decoupe {
    private int lost;
    private String info;

    public Decoupe(int _lost, String _info) {
        this.lost = _lost;
        this.info = _info;
    }

    /*public Decoupe() {
        this.lost = 0;
        this.info = "";
    }*/

    public Decoupe(Decoupe clone) {
        this.lost = clone.getLost();
        this.info = clone.getInfo();
    }

    public int getLost() {
        return lost;
    }

    public String getInfo() {
        return info;
    }

    public void setLost(int lost) {
        this.lost = lost;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String toString(){
        return " La chute est de : " + this.lost+".\n" +
                " Les infos : " + this.info;
    }

    public void fusion(Decoupe toFusion){
        this.info = this.info + " " + toFusion.getInfo();
        this.lost = this.lost + toFusion.getLost();
    }
}
