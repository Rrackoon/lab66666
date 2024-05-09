package org.example.utils;

import java.nio.ByteBuffer;

public   class ByteActions {
    public static ByteBuffer  joinStrings(String[] response) {
        //вычисление общей длины всех строк
        int len=0;
        for (int i = 0; i < response.length; i++) {
            if (response[i].getBytes().length != 0) {
                if (len == 0) {
                    len = response[i].getBytes().length;
                }
                 else {
                    len += ("\n" + response[i]).getBytes().length;
                }
            }
        }
        String resp="";
        //буфер для хранения объединенных строк
        byte[] allResp = new byte[len];
        ByteBuffer buff = ByteBuffer.wrap(allResp);
        //собирать в единый буфер
        for (int i=0;i< response.length;i++){
            if (response[i].getBytes().length!=0){
                if (i == 0) {
                    buff.put(response[i].getBytes());
                    resp=response[i];
                }
                 else {
                    buff.put(("\n"+response[i]).getBytes());
                    resp += "\n"+response[i];

                }
            }
        }
        //вывод объединенных строк экран для отладки
        System.out.println("Ответ: "+resp);
        return buff;
    }

    //split разделяет ByteBuffer на несколько буферов с заданным размером
    public static ByteBuffer[] split(ByteBuffer src, int unitSize) {
        // Проверяем, не больше ли размер разделения чем исходный буфер
        int limit = src.limit();
        if (unitSize >= limit) {
            ByteBuffer[] ret = new ByteBuffer[1];
            ret[0]=src;
            return ret;
        }
        // Рассчитываем количество получаемых буферов
        int size = (int) (Math.ceil((double) src.limit() / (double) unitSize));
        ByteBuffer[] ret = new ByteBuffer[size];
        int srcIndex = 0;
        // Разделяем исходный буфер на буферы с заданным размером
        for (int i = 0; i < size; i++) {
            int bufferSize = unitSize;
            if (i == size - 1) {
                bufferSize = src.limit() % unitSize;
            }

            byte[] dest = new byte[bufferSize];
            System.arraycopy(src.array(), srcIndex, dest, 0, dest.length);
            srcIndex = srcIndex + bufferSize;

            ret[i] = ByteBuffer.wrap(dest);
            ret[i].position(0);
            ret[i].limit(ret[i].capacity());
        }

        return ret;
    }
}
