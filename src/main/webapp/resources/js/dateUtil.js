Date.prototype.format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}

var DateUtil = function () {

	return {
		month : {
				'01':'一月'	,
				'02':'二月'	,
				'03':'三月'	,
				'04':'四月'	,
				'05':'五月'	,
				'06':'六月'	,
				'07':'七月'	,
				'08':'八月'	,
				'09':'九月'	,
				'10':'十月'	,
				'11':'十一月',
				'12':'十二月'
		},
		/**
		根据本周星期几获取日期
		**/
		getDateByDayOfWeek : function(dayOfWeek){

			var day=new Date();
			var todayOfWeek = (day.getDay() == 0) ? 7 : day.getDay();
			day.setDate(day.getDate() + (dayOfWeek - todayOfWeek));
			return day;
		},

		getDayOfWeek : function(date){

			var dayOfWeek = (date.getDay() == 0) ? 7 : date.getDay();
			return dayOfWeek;
		},

		formatDate : function(date){
			var year = date.getFullYear();
			var month = date.getMonth() + 1;
			var day = date.getDate();
			if (month < 10){
				month = "0" + month;
			}
			if (day < 10){
				day = "0" + day;
			}
			return year + '-' + month + '-' + day;
		},

		formatDateTime : function(dateTime){
			var year = dateTime.getFullYear();
			var month = dateTime.getMonth() + 1;
			var day = dateTime.getDate();
			var hour = dateTime.getHours();
			var minute = dateTime.getMinutes();
			var second = dateTime.getSeconds();

			if (month < 10){
				month = "0" + month;
			}
			if (day < 10){
				day = "0" + day;
			}
			if (hour < 10){
				hour = "0" + hour;
			}
			if (minute < 10){
				minute = "0" + minute;
			}
			if (second < 10){
				second = "0" + second;
			}

			return year + '-' + month + '-' + day + " " + hour + ":" + minute + ":" + second;
		},

		diff_day : function(beginStrDate, endStrDate){

			var bDateArr = beginStrDate.split("-"); 
			var eDateArr = endStrDate.split("-"); 

			var bDate = new Date(bDateArr[0], bDateArr[1]-1, bDateArr[2]);
  			var eDate = new Date(eDateArr[0], eDateArr[1]-1, eDateArr[2]);

  			var day = 24 * 60 * 60 *1000; 
  			var diff = (eDate.getTime() - bDate.getTime());

  			return diff / day;  
		},

		getWeekDay : function(day){

			var dayArr = day.split("-"); 
			var date = new Date(dayArr[0], dayArr[1]-1, dayArr[2]);

			var dayOfWeek = (date.getDay() == 0) ? 7 : date.getDay();
			
			var bDays = -(dayOfWeek - 1);
			var eDays = 7- dayOfWeek;

			var iDate = date.valueOf(); 

  			var bDate = iDate + bDays * 24 * 60 * 60 * 1000; 
   			bDate = new Date(bDate); 

			var eDate = iDate + eDays * 24 * 60 * 60 * 1000; 
   			var eDate = new Date(eDate); 

   			var b=DateUtil.formatDate(bDate);
   			var e=DateUtil.formatDate(eDate);
   			return [b,e];
		},

		/**
         * 获取上一个月
         *
         * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
         */
        getPreMonth : function(date) {
            var arr = date.split('-');
            var year = arr[0]; //获取当前日期的年份
            var month = arr[1]; //获取当前日期的月份
            var day = arr[2]; //获取当前日期的日
            var days = new Date(year, month, 0);
            days = days.getDate(); //获取当前日期中月的天数
            var year2 = year;
            var month2 = parseInt(month) - 1;
            if (month2 == 0) {
                year2 = parseInt(year2) - 1;
                month2 = 12;
            }
            var day2 = day;
            var days2 = new Date(year2, month2, 0);
            days2 = days2.getDate();
            if (day2 > days2) {
                day2 = days2;
            }
            if (month2 < 10) {
                month2 = '0' + month2;
            }
            var t2 = year2 + '-' + month2 + '-' + day2;
            return t2;
        },
        
        /**
         * 获取下一个月
         *
         * @date 格式为yyyy-mm-dd的日期，如：2014-01-25
         */        
        getNextMonth : function(date) {
            var arr = date.split('-');
            var year = arr[0]; //获取当前日期的年份
            var month = arr[1]; //获取当前日期的月份
            var day = arr[2]; //获取当前日期的日
            var days = new Date(year, month, 0);
            days = days.getDate(); //获取当前日期中的月的天数
            var year2 = year;
            var month2 = parseInt(month) + 1;
            if (month2 == 13) {
                year2 = parseInt(year2) + 1;
                month2 = 1;
            }
            var day2 = day;
            var days2 = new Date(year2, month2, 0);
            days2 = days2.getDate();
            if (day2 > days2) {
                day2 = days2;
            }
            if (month2 < 10) {
                month2 = '0' + month2;
            }
        
            var t2 = year2 + '-' + month2 + '-' + day2;
            return t2;
        },

		getChineseMonth : function(m){
			if(m.length>0){				
				return this.month[m];
			}
			return '';
		},

		getMonthCnName : function(strDate){
			
			var month = strDate.substring(5,7);

			if (month == '01'){
				return '一月';
			} else if (month == '02'){
				return '二月';
			} else if (month == '03'){
				return '三月';
			} else if (month == '04'){
				return '四月';
			} else if (month == '05'){
				return '五月';
			} else if (month == '06'){
				return '六月';
			} else if (month == '07'){
				return '七月';
			} else if (month == '08'){
				return '八月';
			} else if (month == '09'){
				return '九月';
			} else if (month == '10'){
				return '十月';
			} else if (month == '11'){
				return '十一月';
			} else if (month == '12'){
				return '十二月';
			} else {
				return '';
			}
		},

		getDayName : function(strDate){
			var day = strDate.substring(8,10);
			return parseInt(day);
		},

		format : function (fmt) { //author: meizz 
		    var o = {
		        "M+": this.getMonth() + 1, //月份 
		        "d+": this.getDate(), //日 
		        "h+": this.getHours(), //小时 
		        "m+": this.getMinutes(), //分 
		        "s+": this.getSeconds(), //秒 
		        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
		        "S": this.getMilliseconds() //毫秒 
		    };
		    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
		    for (var k in o)
		    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
		    return fmt;
		},

		formatDay : function(strDate, format){
			var date = new Date(strDate); 			
			var text = date.format(format);  
			return text;
		}
	};

}();
