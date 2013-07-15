function setHome(obj, url) {
	try {
		obj.style.behavior = 'url(#default#homepage)';
		obj.setHomePage(url);
	} catch (e) {
		if (window.netscape) {
			try {
				netscape.security.PrivilegeManager.enablePrivilege("UniversalXPConnect");
			} catch (e) {
				alert("此操作被浏览器拒绝！\n请在浏览器地址栏输入“about:config”并回车\n然后将 [signed.applets.codebase_principal_support]的值设置为'true',双击即可。");
			}
			var prefs = Components.classes['@mozilla.org/preferences-service;1'].getService(Components.interfaces.nsIPrefBranch);
			prefs.setCharPref('browser.startup.homepage', vrl);
		}
	}
}
function addFavorite(url, name) {
	try {
		window.external.addFavorite(url, name);
	} catch (e) {
		try {
			window.sidebar.addPanel(name, url, "");
		} catch (e) {
			alert("加入收藏失败，请使用Ctrl+D进行添加");
		}
	}
}

function playImage(){
	var t = n = 0, count; 
	count=$("#banner_list a").length; 
	$("#banner_list a:not(:first-child)").hide(); 
	$("#banner_info").html($("#banner_list a:first-child").find("img").attr('alt')); 
	$("#banner_info").click(function(){window.open($("#banner_list a:first-child").attr('href'), "_blank")}); 
	$("#banner li").click(function() { 
		var i = $(this).text() - 1;//获取Li元素内的值，即1，2，3，4 
		n = i; 
		if (i >= count) return; 
		$("#banner_info").html($("#banner_list a").eq(i).find("img").attr('alt')); 
		$("#banner_info").unbind().click(function(){window.open($("#banner_list a").eq(i).attr('href'), "_blank")})
		$("#banner_list a").filter(":visible").fadeOut(500).parent().children().eq(i).fadeIn(1000); 
		$(this).css({"background":"#be2424",'color':'#000'}).siblings().css({"background":"#6f4f67",'color':'#fff'}); 
	});
	t = setInterval(function showAuto() 
	{ 
		n = n >=(count - 1) ? 0 : ++n; 
		$("#banner li").eq(n).trigger('click'); 
	} , 4000); 
	$("#banner").hover(function(){clearInterval(t)}, function(){t = setInterval(function showAuto() 
	{ 
		n = n >=(count - 1) ? 0 : ++n; 
		$("#banner li").eq(n).trigger('click'); 
	} , 4000);}); 
}

function startMarquee(){
	var speed=30;
    var demo = $("#marqueueId");
    var demo1 = $("#marqueueId1");
    var demo2 = $("#marqueueId2");
    demo2.html(demo1.html());
    
    var MyMar=setInterval(function (){
        if(demo.scrollLeft()>=demo1.width())
            demo.scrollLeft(0);
        else{
            demo.scrollLeft(demo.scrollLeft()+1);
        }
    },speed);
    demo.mouseover(function() {
        clearInterval(MyMar);
    });
    demo.mouseout(function() {
        MyMar=setInterval(function(){
            if(demo.scrollLeft()>=demo1.width())
                demo.scrollLeft(0);
            else{
                demo.scrollLeft(demo.scrollLeft()+1);
            }
        },speed);
    });
}