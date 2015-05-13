(function(){
	$(document).ajaxSend(function(e,xhr,opt) {
		if (opt.type == "POST" && $('#_csrf').length > 0){
			var header = $('#_csrf').attr('name');
			var token  = $('#_csrf').attr('content');
			xhr.setRequestHeader(header, token);
		}
	});
})();

var Access = function () {

	var _config = {
		async : true,  // 同步或者异步
		type : 'GET',  // 访问类型,GET 、  POST
		data : null,  // 发送到服务器的数据，发送到服务器的数据。将自动转换为请求字符串格式。GET 请求中将附加在 URL 后。 processData 选项说明以禁止此自动转换。必须为 Key/Value 格式。如果为数组，jQuery 将自动为不同值对应同一个名称。如 {foo:["bar1", "bar2"]} 转换为 '&foo=bar1&foo=bar2'。
		dataType : 'json', // 返回数据的类型 xml,html,script,json ,jsonp,text
		contentType :'application/x-www-form-urlencoded', //默认值: true。默认情况下，通过data选项传递进来的数据，如果是一个对象(技术上讲只要不是字符串)，都会处理转化成一个查询字符串，以配合默认内容类型 "application/x-www-form-urlencoded"。如果要发送 DOM 树信息或其它不希望转换的信息，请设置为 false。
		timeout : 1000 * 60 * 10 , // 超时设置 10分钟
		success : function(){}  //成功回调函数
	};
	
	var vu = function(v){
		 if(typeof(v) != 'undefined' && v != null ){
			 return true;
		 }
		 return false;
	 };

	var get = function(option){
		if(typeof(option.url)=='undefined'){ 
			alert("url 未定义"); 
		}

		option.url = addTimestamp(option.url);

		jQuery.get(option.url,vu(option.data)?option.data:{},option.callback,vu(option.dataType)?option.dataType:"json");
	};
	
	var	post = function(option){
		if(typeof(option.url)=='undefined'){ 
			alert("url 未定义"); 
		}
		if (typeof(option.data)=='undefined'){
			option.data = {};
		}

		option.url = addTimestamp(option.url);

		jQuery.post(option.url,vu(option.data)?option.data:{},option.callback,vu(option.dataType)?option.dataType:"json");
	};
	
	var ajax = function(option){
		if(typeof(option.url)=='undefined'){
			alert("url 未定义");
		}

		var config = {};
		$.extend(true, config, _config, option);

		config.url = addTimestamp(config.url);

		jQuery.ajax(config);
	};

	var addTimestamp = function(url){
		if (url.indexOf('?') > 0){
			url += "&_t="+ (new Date().getTime());
		}else{
			url += "?_t="+ (new Date().getTime());
		}
		return url;
	}

    return {
        get : function (option) {
            get(option);
        },
        post : function (option) {
            post(option);
        },
        ajax : function(option){
        	ajax(option);
        }
    };

}();
