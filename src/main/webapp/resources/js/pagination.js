var Pagination = function () {

	return {

		page : function(currentPage,total,fn, limit, position){
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
	        //var totalGroup = Math.ceil(pageCount/ pageCountOfGroup );       //共几组

	        var startPage = (currentGroup - 1) * pageCountOfGroup + 1;
			var endPage = currentGroup * pageCountOfGroup;
			endPage = (endPage > pageCount) ? pageCount : endPage;

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
	    },

		scroll : function(currentPage,total,fn, limit, position){
			if (limit =='left' || limit == 'right' || limit == 'center'){
				position = limit;
			}
			if (isNaN(limit)){
				limit = 10; //每页10条，
			}

			if ((currentPage * limit) >= total){
				return null;
			}

			var nextPage = currentPage + 1;
			var lastCount = total - (currentPage*limit);
			var $btn = $('<div class="row"><div class="col-md-4 col-md-offset-4 col-sm-4 col-sm-offset-4 col-xs-12"><button class="btn btn-default btn-block">更多 ('+lastCount+')</button></div></div>');
			$btn.find('button').on('click',{page:nextPage}, function(e){
					$(this).button('loading');
					$(this).html('<div> <i class="icon-spinner icon-spin"></i> loading...</div>');
					fn({data:{page:nextPage}});
				});

			var style= (position !='left' && position != 'right' && position != 'center') ? 'text-align:center;' : ('text-align:'+position);
			var page = $('<nav style="'+style+' padding:15px 15px;"></nav>').append($btn);

			$(window).unbind('scroll',Pagination.scrollAuto ).bind('scroll',Pagination.scrollAuto );

			return page;
		},

		scrollAuto : function(){
			var scrollTop = $(window).scrollTop();
			var scrollHeight = $(document).height();
			var windowHeight = $(window).height();
			if (scrollTop + windowHeight == scrollHeight) {
				$('#page').find('button').button('loading');
				$('#page').find('button').html('<div> <i class="icon-spinner icon-spin"></i> loading...</div>');
				setTimeout(function(){
					$('#page').find('button').trigger('click');
				}, 500);
			}
		}
	};

}();
