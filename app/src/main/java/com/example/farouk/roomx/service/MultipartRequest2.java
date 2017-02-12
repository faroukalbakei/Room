package com.example.farouk.roomx.service;

/**
 * Created by hamdy on 6/20/15.
 */

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MultipartRequest2 extends Request<String> {

    MultipartEntityBuilder entity = MultipartEntityBuilder.create();
    HttpEntity httpentity;
     static String FILE_PART_NAME = "cover";
     static String FILE_ARRAY_PART_NAME = "upload";

     final Response.Listener<String> mListener;
     final File mFilePart;
     final ArrayList<String> fileArray;
     final Map<String, String> mStringPart;
    MultipartProgressListener multipartProgressListener;

    public MultipartRequest2(String url,

                             Response.Listener<String> listener,
                             Response.ErrorListener errorListener,

                             String file_part_name,
                             String file_array_part_name,

                             File file,
                             ArrayList<String> file_array,
                             Map<String, String> mStringPart,
                             MultipartProgressListener multipartProgressListener
    ) {
        super(Method.POST, url, errorListener);

        this.mListener = listener;
        this.mFilePart = file;
        this.fileArray = file_array;
        this.FILE_ARRAY_PART_NAME=file_array_part_name;
        this.FILE_PART_NAME=file_part_name;
        this.mStringPart = mStringPart;
        this.multipartProgressListener=multipartProgressListener;
        this.entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        this.entity.setCharset(Charset.defaultCharset());
        buildMultipartEntity();
    }

    //multiple file
    public MultipartRequest2(String url,

                             Response.Listener<String> listener,
                             Response.ErrorListener errorListener,

                             String file_array_part_name,

                             ArrayList<String> file_array,
                             Map<String, String> mStringPart,
                             MultipartProgressListener multipartProgressListener
    ) {
        super(Method.POST, url, errorListener);

        this.mListener = listener;
        this.mFilePart = null;
        this.fileArray = file_array;
        this.FILE_ARRAY_PART_NAME=file_array_part_name;
        this.FILE_PART_NAME="";
        this.mStringPart = mStringPart;
        this.multipartProgressListener=multipartProgressListener;
        entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entity.setCharset(Charset.defaultCharset());
        buildMultipartEntity();
    }

    //single file
    public MultipartRequest2(String url,

                             Response.Listener<String> listener,
                             Response.ErrorListener errorListener,

                             String file_part_name,

                             File file,
                             Map<String, String> mStringPart,
                             MultipartProgressListener multipartProgressListener
    ) {
        super(Method.POST, url, errorListener);

        this.mListener = listener;
        this.mFilePart = file;
        this.fileArray = null;
        this.FILE_ARRAY_PART_NAME="";
        this.FILE_PART_NAME=file_part_name;
        this.mStringPart = mStringPart;
        this.multipartProgressListener=multipartProgressListener;
        this.entity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        this.entity.setCharset(Charset.defaultCharset());
        buildMultipartEntity();
    }
    public void addStringBody(String param, String value) {
        mStringPart.put(param, value);
    }

    private void buildMultipartEntity() {
        if(mFilePart!=null) {
            entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
        }

       if(fileArray!=null){
            for (int i = 0; i < fileArray.size(); i++) {
               entity.addPart(FILE_ARRAY_PART_NAME+"["+i+"]", new FileBody(new File(fileArray.get(i))));
            }
      }

        for (Map.Entry<String, String> entry : mStringPart.entrySet()) {
            entity.addTextBody(entry.getKey(), entry.getValue(), ContentType.create("text/plain", Charset.forName("UTF-8")));
//			entity.addTextBody(entry.getKey(), entry.getValue());

        }
		
    }

    @Override
    public String getBodyContentType() {
        return httpentity.getContentType().getValue();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
//        ByteArrayOutputStream bos = new ByteArrayOutputStream();
//        try {
//            httpentity = entity.build();
//            httpentity.writeTo(bos);
//        } catch (IOException e) {
//            VolleyLog.e("IOException writing to ByteArrayOutputStream");
//        }
//        return bos.toByteArray();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {

            httpentity = entity.build();
            Log.d("getBody",httpentity.getContentLength()+"");
            //Log.d("file",mFilePart.length()+"");
            httpentity.writeTo(new CountingOutputStream(bos, httpentity.getContentLength(),multipartProgressListener));
        } catch (IOException e) {
            VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        mListener.onResponse(response);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> header = new HashMap<String, String>();
        header.put("Content-Type","application/x-www-form-urlencoded");
        header.put("Accept","application/json");
        return super.getHeaders();
    }






    public interface MultipartProgressListener {
        void transferred(long transfered, int progress);
    }

    public class CountingOutputStream extends FilterOutputStream {
        private final MultipartProgressListener progListener;
        private long transferred;
        private long fileLength;

        public CountingOutputStream(final OutputStream out, long fileLength,
                                    final MultipartProgressListener listener) {
            super(out);
            this.fileLength = fileLength;
            this.progListener = listener;
            this.transferred = 0;
        }

        public void write(byte[] b, int off, int len) throws IOException {
            out.write(b, off, len);
            if (progListener != null) {
                this.transferred += len;
                int prog = (int) (transferred * 100 / fileLength);
                this.progListener.transferred(this.transferred, prog);
            }
        }

        public void write(int b) throws IOException {
            out.write(b);
            if (progListener != null) {
                this.transferred++;
                int prog = (int) (transferred * 100 / fileLength);
                this.progListener.transferred(this.transferred, prog);
            }
        }

    }
}

