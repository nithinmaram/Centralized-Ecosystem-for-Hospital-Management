package com.voidmain.pm.service;

import java.net.*;
import java.io.*;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class SimpleFTPClient {

	private URLConnection m_client;
	private String host;
	private String user;
	private String password;
	private String remoteFile;
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public void setPassword(String p) {
		this.password = p;
	}

	public void setRemoteFile(String d) {
		this.remoteFile = d;
	}

	public synchronized boolean uploadFile(InputStream is) {
	
		try {

			BufferedInputStream bis = new BufferedInputStream(is);
			
			OutputStream os = m_client.getOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(os);
			
			byte[] buffer = new byte[1024];
			int readCount;

			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
			
			bos.close();
			return true;
		
		} catch (Exception ex) {

			ex.printStackTrace();
			
			return false;
		}
	}

	public synchronized boolean downloadFile(String localfilename) {
		try {
	
			InputStream is = m_client.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			
			OutputStream os = new FileOutputStream(localfilename);
			BufferedOutputStream bos = new BufferedOutputStream(os);

			byte[] buffer = new byte[1024];
			int readCount;

			while ((readCount = bis.read(buffer)) > 0) {
				bos.write(buffer, 0, readCount);
			}
		
			bos.close();
			is.close();
			
			return true;
		} catch (Exception ex) {
		
			ex.printStackTrace();

			return false;
		}
	}


	public static void encrypt(InputStream is, OutputStream os,String mykey) throws Throwable {
		encryptOrDecrypt(mykey, Cipher.ENCRYPT_MODE, is, os);
	}

	public static void decrypt(InputStream is, OutputStream os,String mykey) throws Throwable {
		encryptOrDecrypt(mykey, Cipher.DECRYPT_MODE, is, os);
	}


	public static void encryptOrDecrypt(String key, int mode, InputStream is, OutputStream os) throws Throwable {

		DESKeySpec dks = new DESKeySpec(key.getBytes());
		SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
		SecretKey desKey = skf.generateSecret(dks);

		Cipher cipher = Cipher.getInstance("DES"); // DES/ECB/PKCS5Padding for SunJCE

		if (mode == Cipher.ENCRYPT_MODE) {
			cipher.init(Cipher.ENCRYPT_MODE, desKey);
			CipherInputStream cis = new CipherInputStream(is, cipher);
			doCopy(cis, os);
		} else if (mode == Cipher.DECRYPT_MODE) {
			System.out.println("in decripton");
			cipher.init(Cipher.DECRYPT_MODE, desKey);
			CipherOutputStream cos = new CipherOutputStream(os, cipher);
			doCopy(is, cos);
		}
	}

	public static void doCopy(InputStream is, OutputStream os) throws IOException {
		byte[] bytes = new byte[64];
		int numBytes;
		while ((numBytes = is.read(bytes)) != -1) {

			System.out.println("in while now ");
			os.write(bytes, 0, numBytes);
		}
		os.flush();
		os.close();
		is.close();
	}


	public synchronized boolean connect() {
		
		try {
	
			URL url = new URL("ftp://" + user + ":" + password + "@" + host + "/" + remoteFile + ";type=i");
			
			m_client = url.openConnection();
			
			return true;

		} catch (Exception ex) {
			
			ex.printStackTrace();

			return false;
		}

	}
}

