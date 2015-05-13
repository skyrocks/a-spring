var Tip = function() {

	
	function load_js(url) {
		//scripts = document.getElementsByTagName('script')[0];
		var script = document.createElement('script');
		script.async = false;
		script.src = url;
		document.getElementsByTagName('head')[0].appendChild(script);
	}
	function load_css(url) {
		//scripts = document.getElementsByTagName('script')[0];
		var script = document.createElement('link');
		script.rel = "stylesheet"
		script.async = false;
		script.href = url;
		document.getElementsByTagName('head')[0].appendChild(script);
	}
	return {

		loadResource: function() {
			load_js("resources/plugins/jquery-gritter/js/jquery.gritter.js");
			load_css("resources/plugins/jquery-gritter/css/jquery.gritter.css");
		},

		info:function(info){
			var text = '<p style="color:#eee;"><i class="icon-star"></i> '+info+' </p>';
			var option = {text:text, class_name: 'gritter-info gritter-center'};
			$.gritter.add(option);
		},

		success:function(){
			var text = '<p style="color:#eee;"><i class="icon-ok"></i> Success! </p>';
			var option = {text:text, class_name: 'gritter-success gritter-center'};
			$.gritter.add(option);
		},

		error:function(error,title){
			var text = '<p style="color:#eee;"><i class="icon-remove"></i> '+error+'</p>';
			var option = {time:6000, text:text, class_name: 'gritter-error gritter-center'};
			$.gritter.add(option);
		},

		warning:function(warning){
			var text = '<p style="color:#eee;"><i class="icon-info-sign"></i> '+warning+' </p>';
			var option = {time:3000, text:text, class_name: 'gritter-warning gritter-center'};
			$.gritter.add(option);
		}
	}
}();

Tip.loadResource();
