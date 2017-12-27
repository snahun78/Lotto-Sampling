/**
* Alopex UI v2.9.2
* http://ui.alopex.io
*
* Copyright (c) 2017 SK Holdings C&C. All rights reserved.
*
* This software is the confidential and proprietary information of SK Holdings C&C.
* You shall not disclose such confidential information and shall use it only in
* accordance with the terms of the license agreement you entered into with SK Holdings C&C.
*
* Date :  2017-12-08 16:39:36 GMT+0900 (대한민국 표준시)
**/
$a.setup({
    defaultComponentClass: {
    	webEditor: 'WebEditor',
    	webeditor: 'Webeditor'
    }
});


$a.widget.webEditor = $a.widget.webeditor = $a.inherit($a.widget.object, {
    widgetName: 'webeditor',
    properties: {
		height: 300
    },
    init: function(el, options){
		var opts = $.extend(true, {}, this.properties, options);
		$(el).webeditor(opts);
    }
});