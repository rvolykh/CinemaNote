package com.pikaso.home.cinemanote.view;

import java.util.Date;

import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import lombok.Data;

/**
 * Ping view
 * @author pikaso
 */
@Data
@ApiObject(name="Ping", description="Simple ping response object")
public class PingDTO {
	@ApiObjectField(description = "the call id")
	private long id;
	@ApiObjectField(description = "server time in milliseconds")
	private Date time;
}
