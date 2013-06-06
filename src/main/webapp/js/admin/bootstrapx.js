function to(url){
	location.href=url;
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
		    $(formId + '_message').css('display','none');
		    $(formId).modal('show');
		    $('#progress').modal('hide');
		    return true;
		  });
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
	    $(formId + '_message').css('display','none');
	    $(formId).modal('show');
	    return true;
	});
	return true;
}

function ajaxSubmit(id, msgId){
	submit(id, msgId, 1000)
}
function ajaxSubmit(id, msgId, time){
	var data = jQuery(id).serializeArray(); 
	jQuery.post(jQuery(id).attr("action"),data, function (responseResult) {
		var dataObj = responseResult;
		if(dataObj.result==="OK"){
			jQuery(msgId).removeClass('alert-error');
			jQuery(msgId).addClass('alert-success');
			jQuery(msgId).html("<strong>成功:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
			setTimeout(function reloadPage(){location.reload();}, time);
		}else{
			jQuery(msgId).removeClass('alert-success');
			jQuery(msgId).addClass('alert alert-error');
			jQuery(msgId).html("<strong>错误:</strong>" + dataObj.message);
			jQuery(msgId).css('display','block');
		}
	});
}