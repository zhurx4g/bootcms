function privilege(node){
	$('#progress').modal({backdrop:false,show:true});
	var url = $(node).attr('data-url');
	var key = eval('(' + $(node).attr('data-param')+')');
	var formId = $(node).attr('href');//keep compate for bootstrap

	var key = eval('(' + $(node).attr('data-param')+')');
	$(formId + " [name='userId']").val(key.userId);
	$.getJSON(url,key,function (data){
		var zTreeObj = $.fn.zTree.getZTreeObj("privilegeTree");
		zTreeObj.checkAllNodes(false);
		var nodes = zTreeObj.getNodes();
		for(var i=0; i<nodes.length; i++){
			var isCheck = false;
			for(var j=0;j<data.length;j++){
				var node = data[j];
				if(nodes[i].id==node['privilegeId']){
					isCheck = true;
					break;
				}
			}
			if(isCheck){
				$('#privilege_'+ nodes[i].id).val(nodes[i].id);
				zTreeObj.checkNode(nodes[i],true,true);
			}else{
				$('#privilege_'+ nodes[i].id).val('-' + nodes[i].id);				
			}
		}
		$(formId + '_message').css('display','none');
		$(formId).modal('show');
		$('#progress').modal('hide');
	});
	return true;
}

function ajaxSubmitPriv(id, msgId){
	$(id + " :input[name='privId']").each(function(){
		$(this).val('-'+$(this).attr("data-id"));
	    return true;
	});
	var zTreeObj = $.fn.zTree.getZTreeObj("privilegeTree");
	var nodes = zTreeObj.getCheckedNodes();
	for(var i=0; i<nodes.length; i++){
		$('#privilege_'+ nodes[i].id).val(nodes[i].id);
	}
	ajaxSubmit(id, msgId)
}