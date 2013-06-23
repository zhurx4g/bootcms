package com.googlecode.bootstrapx.web.controllers;

import org.springframework.beans.factory.annotation.Autowired;

import com.googlecode.bootstrapx.service.ConfigService;
import com.googlecode.bootstrapx.service.FriendLinkService;
import com.googlecode.bootstrapx.service.NavigateService;
import com.googlecode.bootstrapx.service.SubSystemService;

public class CommonController extends AbstractController {
	@Autowired
	protected SubSystemService subSystemService;

	@Autowired
	protected ConfigService configService;
	
	@Autowired
	protected NavigateService navigateService;

	@Autowired
	protected FriendLinkService friendLinkService;
}
