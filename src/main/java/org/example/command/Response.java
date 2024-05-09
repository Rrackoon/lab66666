package org.example.command;

import org.example.utils.ByteActions;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.LinkedList;
import java.util.Collections;

public class Response implements Serializable {
    private byte[] message;
    private int rcount;
    private int rnumber;

    public Response(byte[] message, int rcount, int rnumber) {
        this.message = message;
        this.rcount = rcount;
        this.rnumber = rnumber;
    }

    public byte[] getMessage() {
        return  this.message;
    }

    public int getRcount() {
        return this.rcount;
    }

    public int getRnumber() {
        return this.rnumber;
    }

    public void setRcount(int rcount) {
        this.rcount = rcount;
    }

    public void setRnumber(int rnumber) {
        this.rnumber = rnumber;
    }

    public static Response[] createResponses(String[] response){

        ByteBuffer resp= ByteActions.joinStrings(response);
        ByteBuffer[] resps = ByteActions.split(resp,394);
        Response[] respArr= new Response[resps.length];
        for (int i=0;i<resps.length;i++)
        {
            respArr[i]= new Response(resps[i].array(),i,resps.length);
        }

        return  respArr;
    }
}
