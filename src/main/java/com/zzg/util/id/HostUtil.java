package com.zzg.util.id;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HostUtil {

	private final static Logger LOG = LoggerFactory.getLogger(HostUtil.class);

	public static String getHostAddress() {
		try {
			Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();

			while (interfaces.hasMoreElements()) {
				NetworkInterface networkInterface = interfaces.nextElement();
				if (!networkInterface.isUp() || networkInterface.isLoopback() || networkInterface.isVirtual()) {
					continue;
				}

				Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();
				while (addresses.hasMoreElements()) {
					String hostAddress = addresses.nextElement().getHostAddress();
					if (ValidationUtil.checkIpAddress(hostAddress)) {
						return hostAddress;
					}
				}
			}
		} catch (Exception ex) {
			LOG.error("Failed to get host address.", ex);
		}

		return "";
	}

	public static void main(String[] args) {
		System.out.println(HostUtil.getHostAddress());
	}
}
