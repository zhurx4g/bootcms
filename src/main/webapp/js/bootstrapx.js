function to(url){
	location.href=url;
}

function values(selector){
	var sids = "";
	jQuery(selector).each(function(){
		if(jQuery(this).prop('checked')){
			if(sids===''){
				sids = jQuery(this).val();
			}else{
				sids = sids + "," + jQuery(this).val();
			}
		}
	});
	return sids;
}

function edit(node){
	$('#progress').modal({backdrop:false,show:true});
	var url = $(node).attr('data-url');
	var key = eval('(' + $(node).attr('data-param')+')');
	var formId = $(node).attr('href');//keep compate for bootstrap
	$.getJSON(url,key,function (data){
		$(formId + " :input").each(function(){
		    if($(this).attr("type")==='button'){
		    	return true;
		    }else if($(this).attr("type")==='checkbox'){
				if(data[$(this).attr("name")]==1){
					$(this).prop("checked", true);
				}else{
					$(this).prop("checked", false);
				}
		    }else{
		    	$(this).val(data[$(this).attr("name")]);
		    }
		    return true;
		  });
		$(formId + '_message').css('display','none');
		$(formId).modal('show');
		$('#progress').modal('hide');
	});
	return true;
}
function remove(node){
	var formId = $(node).attr('href');//keep compate for bootstrap
	$(formId+'_title').html($(node).attr("data-title"));
	$(formId + '_message').css('display','none');
	data = eval('('+ $(node).attr("data-param") +')');
	$(formId + " :input").each(function(){
	    if($(this).attr("type")==='button'){
	    	return true;
	    }else if($(this).attr("type")==='checkbox'){
			if(data[$(this).attr("name")]==1){
				$(this).prop("checked", true);
			}else{
				$(this).prop("checked", false);
			}
	    }else{
	    	$(this).val(data[$(this).attr("name")]);
	    }
	    return true;
	});
	$(formId + '_message').css('display','none');
	$(formId).modal('show');
	return true;
}

function reloadPage(){
	location.reload(true);
}

function ajaxSubmitForm(id, time, onSuccess){
	$('#progress').modal({backdrop:'static'});
	var msgId ='#confirm_message';
	var data = jQuery(id).serializeArray(); 
	if(time === undefined) {
		time = 1000;
	}
	if(onSuccess === undefined) {
		onSuccess = reloadPage;
	}
	$.post(jQuery(id).attr("action"),data, function (responseResult) {
		$('#progress').modal('hide');
		var dataObj = responseResult;
		if(dataObj.result==="OK"){
			jQuery(msgId).removeClass('alert-error');
			jQuery(msgId).addClass('alert-success');
			jQuery(msgId).html("<strong>成功:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
			setTimeout(onSuccess, time);
		}else{
			jQuery(msgId).removeClass('alert-success');
			jQuery(msgId).addClass('alert alert-error');
			jQuery(msgId).html("<strong>错误:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
		}
		jQuery('#confirm').modal({backdrop:'static'});
	});
}

function ajaxSubmitJSON(url, data, time, onSuccess){
	$('#progress').modal({backdrop:'static'});
	var msgId ='#confirm_message';
	if(time === undefined) {
		time = 1000;
	}
	if(onSuccess === undefined) {
		onSuccess = reloadPage;
	}
	$.post(url,data, function (responseResult) {
		$('#progress').modal('hide');
		var dataObj = responseResult;
		if(dataObj.result==="OK"){
			jQuery(msgId).removeClass('alert-error');
			jQuery(msgId).addClass('alert-success');
			jQuery(msgId).html("<strong>成功:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
			setTimeout(onSuccess, time);
		}else{
			jQuery(msgId).removeClass('alert-success');
			jQuery(msgId).addClass('alert alert-error');
			jQuery(msgId).html("<strong>错误:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
		}
		jQuery('#confirm').modal({backdrop:'static'});
	});
}

function ajaxSubmit(id, msgId, time, callback){
	if(time === undefined) {
		time = 1000;
	}
	if(callback === undefined) {
		callback = reloadPage;
	}
	var data = jQuery(id).serializeArray(); 
	jQuery.post(jQuery(id).attr("action"),data, function (responseResult) {
		var dataObj = responseResult;
		if(dataObj.result==="OK"){
			jQuery(msgId).removeClass('alert-error');
			jQuery(msgId).addClass('alert-success');
			jQuery(msgId).html("<strong>成功:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
			setTimeout(callback, time);
		}else{
			jQuery(msgId).removeClass('alert-success');
			jQuery(msgId).addClass('alert alert-error');
			jQuery(msgId).html("<strong>错误:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
		}
	});
}

function ajaxLoadForm(url,key,formId,callback){
	ajaxLoad(url, key,formId + " :input", callback);
}

function ajaxLoad(url,key,selector,callback){
	jQuery.getJSON(url, key, function (data){
		jQuery(selector).each(function(){
		    if(jQuery(this).attr("type")==='button'){
		    	return true;
		    }else if(jQuery(this).attr("type")==='checkbox'){
				if(data[jQuery(this).attr("name")]==1){
					jQuery(this).prop("checked", true);
				}else{
					jQuery(this).prop("checked", false);
				}
		    }else{
		    	jQuery(this).val(data[$(this).attr("name")]);
		    }
		    return true;
		  });
		if(callback !== undefined){
			callback(data);
		}
		jQuery('#progress').modal('hide');
	});
}