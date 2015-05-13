var Page = function () {

	return {

		create : function(currentPage,total,fn, limit, position){
			if (limit =='left' || limit == 'right' || limit == 'center'){
	        	position = limit;
	        }
			if (isNaN(limit)){
				limit = 10; //每页10条，
			}
			var pageCountOfGroup = 10;  //每组显示10页, 10的倍数

	        var ul = $('<ul class="pagination"></ul>');
	        
	        var li = $('<li><a href="javascript:void(0);">First</a></li>');
	        li.find('a').on('click',{page:1},fn);
	        ul.append(li);

	        var pageCount = Math.ceil(total/limit); //共pageCount页 
	       
	        var currentGroup = Math.ceil(currentPage/ pageCountOfGroup );   //计算当前页所在第几组
	        var totalGroup = Math.ceil(pageCount/ pageCountOfGroup );       //共几组

	        var startPage = (currentGroup - 1) * pageCountOfGroup + 1;
			var endPage = currentGroup * pageCountOfGroup;
			endPage = (endPage > totalGroup) ? endPage : totalGroup;

	        if (startPage > 1){
	            var li = $('<li><a href="javascript:void(0);">&laquo;</a></li>');
	            li.find('a').on('click',{page:startPage-1},fn);
	            ul.append(li);
	        }
	        for(var i=startPage; i<=endPage; i++){
	            var li = $('<li><a href="javascript:void(0);">'+i+'</a></li>');
	            li.find('a').on('click',{page:i},fn);
	            ul.append(li);
	            if (i == currentPage){
	                li.addClass('active').find('a').append($('<span class="sr-only">(current)</span>'));
	            }
	        }
	        if (endPage < pageCount){
	            var li = $('<li><a href="javascript:void(0);">&raquo;</a></li>');
	            li.find('a').on('click',{page:endPage+1},fn);
	            ul.append(li);
	        }

	        li = $('<li><a href="javascript:void(0);">End</a></li>');
	        li.find('a').on('click',{page:pageCount},fn);
	        ul.append(li);

	        var style= (position !='left' && position != 'right' && position != 'center') ? 'text-align:center;' : ('text-align:'+position);
	        var page = $('<nav style="'+style+'"></nav>').append(ul);
	        
	        return page;
	    }
	};

}();
