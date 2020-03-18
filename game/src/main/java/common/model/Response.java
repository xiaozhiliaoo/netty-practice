package common.model;

/**
 * Created by lili on 2017/4/23.
 */
public class Response {

    private short moudle;
    private short cmd;
    private byte[] data;
    private int statusCode;//玩家不存在，金币不足等等

    public short getMoudle() {
        return moudle;
    }

    public void setMoudle(short moudle) {
        this.moudle = moudle;
    }

    public short getCmd() {
        return cmd;
    }

    public void setCmd(short cmd) {
        this.cmd = cmd;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getDataLength(){
        if(data==null){
            return 0;
        }
        return this.data.length;
    }
}
