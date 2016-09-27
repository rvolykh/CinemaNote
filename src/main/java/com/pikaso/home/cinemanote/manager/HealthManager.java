package com.pikaso.home.cinemanote.manager;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Component;

import com.pikaso.home.cinemanote.util.DateUtil;
import com.pikaso.home.cinemanote.view.PingDTO;

@Component
public class HealthManager {
	private final AtomicLong counter = new AtomicLong();
	
	/**
	 * Simple ping call
	 * @return the {@link PingDTO}
	 */
	public PingDTO health(){
		PingDTO ping = new PingDTO();
		ping.setTime(DateUtil.now());
		ping.setId(counter.incrementAndGet());
		
		return ping;
	}
}
