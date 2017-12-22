var mid = "fhindex";
function addTab(id,MENU_NAME,MENU_URL){
	if(id != mid){
		$("#"+mid).removeClass();
		mid = id;
	}
	$("#"+id).attr("class","active");
    if(!$('#moduleTab').tabs('exists',MENU_NAME)){
        $('#moduleTab').tabs('add',{
            title:MENU_NAME,
            content:createFrame(MENU_URL),
            closable:true
        });
    }else{
        $('#moduleTab').tabs('select',MENU_NAME);
        var currTab = $('#moduleTab').tabs('getSelected');
        var url = $(currTab.panel('options').content).attr('src');
        $('#moduleTab').tabs('update',{
            tab:currTab,
            options:{
                content:createFrame(url)
            }
        })
    }
    tabClose();
}

function createFrame(url)
{
    var s = '<iframe  scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
    return s;
}

function tabClose()
{
    /*双击关闭TAB选项卡*/
    $(".tabs-inner").dblclick(function(){
        var subtitle = $(this).children(".tabs-closable").text();
        $('#moduleTab').tabs('close',subtitle);
    })

}
var eid = "";
function changeModule(id,name,url){
    $.ajax({
        url : url,
        type: "POST",
        data:{id:id},
        success:function (data) {
            if(data && data.length>0){
                if(id != eid){
                    $("#"+eid).removeClass();
                    eid = id;
                }
                $("#"+id).attr("class","active");
                var modulesHtml = "";
                for(var i=0;i<data.length;i++){
                    var module = data[i];
                    var moduleStr = '<li id="'+module.id+'"><a style="cursor:pointer;" target="mainFrame" onclick="addTab(\''+module.id+'\',\''+module.menuName+'\',\'/'+module.resourceUrl+'\')">' +
                        module.menuName+'</a></li>'
                    modulesHtml +=moduleStr;
                }
                $("#module").html(modulesHtml);
            }
        }
    })
}
