package common.model.fuben.request;


import common.serial.Serializer;

public class FightRequest extends Serializer {

    private int fubenId;

    private int count;

    public int getFubenId() {
        return fubenId;
    }

    public void setFubenId(int fubenId) {
        this.fubenId = fubenId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    protected void read() {
        this.fubenId = readInt();
        this.count = readInt();
    }

    @Override
    protected void write() {
        writeInt(fubenId);
        writeInt(count);
    }
}
