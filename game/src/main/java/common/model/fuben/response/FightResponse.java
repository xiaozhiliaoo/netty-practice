package common.model.fuben.response;


import common.serial.Serializer;

/**
 * Created by lili on 2017/4/23.
 */
public class FightResponse extends Serializer {

    //获取了多少金币
    private int gold;

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    protected void read() {
        this.gold = readInt();
    }

    @Override
    protected void write() {
        writeInt(gold);
    }
}
