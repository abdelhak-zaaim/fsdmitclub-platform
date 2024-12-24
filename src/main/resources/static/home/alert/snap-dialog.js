(function(global) {

    // Default options
    var defaultOptions = {
        rtl : false ,
        type: null,
        title: 'Default Title',
        message: 'This is a default message',
        icon : null ,
        customIcon : null ,

        confirmText: 'OK',
        enableConfirm : true ,
        onConfirm: function() {},

        onCancel: function() {},
        enableCancel: false ,
        cancelText: 'Cancel',


        enableCloseHandler : false ,
        onClose: function() {},

        OutsideClose : false ,
        clickToClose : false ,
        autoClose : false,
        duration: 3000,

        preogressBar : false,
        isDark : false,
        animation : 'slide',
        input : null,
        inputPlcaeholder : '',
        inputMask : null,
        size : null,

    };

    const randomString = (length) => Array.from({length}, () => Math.random().toString(36).charAt(2)).join('');

    var types = ['question','success','info','warning','error'];

    var icons = {
        "success" : "bx-check" ,
        "warning" : "bx-bell" ,
        "error" : "bx-error" ,
        "info" : "bx-info-circle" ,
    };

    var sizes = {
        "sm" : "snapDialog-sm" ,
        "md" : "snapDialog-md" ,
        "lg" : "snapDialog-lg" ,
        "xl" : "snapDialog-xl" ,
        "2xl" : "snapDialog-2xl",
        "3xl" : "snapDialog-2xl",
        "4xl" : "snapDialog-4xl",
    };

    var sizesx = ['sm','md','lg','xl',"2xl","3xl","4xl"];

    var positions = {
        "top" : "snapDialog-slide-center" ,
        "bottom" : "snapDialog-bottom-center" ,
        "center" : "snapDialog-top-left" ,
    };

    // Object to store user-set global options
    var setOptions = null;
    

    function NsDialog() {


        const SnapDialogOptions = (options = null) =>{
            setOptions = options ? Object.assign({}, defaultOptions, options) : null;
        }
    
        const createDialog = (type = null,title = null,  message = null, options = {}) => {
            settings = Object.assign({}, setOptions ? setOptions : defaultOptions , options);

            if (type && type != "html") {
                settings.type = type;
            }else if(type == "html"){
                settings.type = type;
                settings.code = title;
            }
            settings.title = title;
            settings.message = message;
    
            var notClosed = true;
            const key = 'dialog-key-'+randomString(6)
    
            const modal = document.createElement('div');
            modal.classList.add('snapDialog-item' , 'snapDialog-'+settings.type , key );
            modal.classList.add('snapDialog-animation-'+settings.animation);
            if (settings.rtl == true) { modal.classList.add('snapDialog-rtl'); }
            if (settings.clickToClose == true) { modal.classList.add('snapDialog-clickable'); }
            if (settings.isDark == true) { modal.classList.add('snapDialog-dark'); }
            if (settings.preogressBar == false) {  modal.classList.add('progress-hide'); }
            if ( settings.size != null == false) { sizes[settings.size] }

            if(settings.size && sizesx.includes(settings.size) ){
                modal.classList.add(sizes[settings.size]);
            }


            var overlay = document.createElement('div');
            overlay.classList.add('snapDialog-overlay', key);
            document.body.appendChild(overlay);

            overlay = document.querySelector('.snapDialog-overlay');
            
            setTimeout(() => {
                overlay.classList.add('snapDialog-overlay-fade-in');
            }, 100);

            settings.type != 'html' && (modal.innerHTML = `
                <div class="snapDialog-main">
                    <button class="snapDialog-close" snap-alert-close><i class='bx bx-x'></i></button>
                    ${ types.includes(settings.type) || settings.icon ? '<div class="snapDialog-icon">' + (settings.customIcon ? settings.customIcon : '<i class="bx '+ (settings.icon ?? icons[settings.type])+'"></i>') + ' </div>' : ''}
                    <div>
                        ${settings.title ? '<div class="snapDialog-title">'+(settings.title)+'</div>' : ''}
                        ${settings.message ? '<div class="snapDialog-message">'+(settings.message)+'</div>' : ''}
                        ${ settings.input != null ? '<input class="snapDialog-input" '+ (settings.inputMask?'x-mask="'+settings.inputMask+'"':'') +' placeholder="'+ settings.inputPlcaeholder +'" type="'+ settings.input +'">' : '' }
                        <div class="snapDialog-actions">
                        ${ settings.enableConfirm ? '<button class="snapDialog-action snapDialog-action-confirm" snap-alert-confirm >'+ settings.confirmText +'</button>' : ''}
                        ${ settings.enableCancel ?'<button class="snapDialog-action snapDialog-action-cancel" snap-alert-cancel >'+ settings.cancelText +'</button>' : ''}
                        </div>
                    </div>
                </div>
                ${settings.preogressBar == true ? "<div style='--snapDialog-progress-duration:"+settings.duration/1000+"s' class='snapDialog-progress-bar'></div>" : '<br>' }
            `);

            settings.type == 'html' && (modal.innerHTML = `
                <div class="snapDialog-main snapDialog-html">
                    <button class="snapDialog-close" snap-alert-close><i class='bx bx-x'></i></button>
                    ${settings.code}
                </div>
            `);
    
            document.body.appendChild(modal);

            const input = document.querySelector("."+key+" "+".snapDialog-input");
            var inputValue = null;
            if (input) {
                input.addEventListener('input' , ()=>{
                    inputValue = input.value;
                });
            }
            setTimeout(() => {
                modal.classList.add('snapDialog-'+settings.animation+'-in');
            }, 100);
    
            if (settings.autoClose == true) {
                setTimeout(() => {
                    closeAndRemove();
                }, settings.duration);
            }
    
            // Close and remove alert
            function closeAndRemove() {
                modal.classList.remove('snapDialog-'+settings.animation+'-in');
                modal.classList.add('snapDialog-'+settings.animation+'-out');
                
                overlay.classList.remove('snapDialog-overlay-fade-in');
                overlay.classList.add('snapDialog-overlay-fade-out');

                setTimeout(() => {
                    modal.remove();
                    overlay.remove();
                },  settings.animation == 'slide' ? 300 : 200 );

                if (settings.enableCloseHandler && notClosed) {
                    setTimeout(() => {
                        notClosed = false;
                        settings.onClose();
                    } , 50);
                    
                }
            }
    
            // Event listeners...
            modal.querySelector('[snap-alert-confirm]')?.addEventListener('click', () => {
                settings.onConfirm(inputValue);
                closeAndRemove();
            });
    
            modal.querySelector('[snap-alert-cancel]')?.addEventListener('click', () => {
                settings.onCancel(inputValue);
                closeAndRemove();
            });
    
            modal.querySelector('[snap-alert-close] , .snapDialog-clickable')?.addEventListener('click', () => {
                closeAndRemove();
            });

            modal.querySelector('.snapDialog-clickable')?.addEventListener('click', () => {
                closeAndRemove();
            });

            settings.OutsideClose == true && overlay?.addEventListener('click', () => {
                closeAndRemove();
            });
    
        };
    
        function clearAll() {
            const allAlerts = document.querySelectorAll('.snapDialog-container > div');
            
            allAlerts.forEach((alert , key) => {
                console.log("Key : "+key*2);
                setTimeout(() => {
                    alert.classList.remove('snapDialog-'+settings.animation+'-in');
                    alert.classList.add('snapDialog-'+settings.animation+'-out');
                }, (key*20));
                setTimeout(() => {
                    alert.remove();
                }, 300+(key*20));
            });
        
            const allContainers = document.querySelectorAll('.snapDialog-container');

            allContainers.forEach((container , key) => {
                setTimeout(() => {
                    if (!container.hasChildNodes()) {
                        container.remove();
                    }
                }, 2000);
            });
        }
        
        return {
            success: (title, message , options) => createDialog('success', title, message , options),
            error: (title, message , options) => createDialog('error',  title,message , options),
            warning: (title, message , options) => createDialog('warning', title, message , options),
            info: (title, message , options) => createDialog('info', title, message , options),
            alert: (title, message , options) => createDialog(null, title, message , options),
            html: (title , options) => createDialog('html', title , null , options ),
            clearAll : () => clearAll(),
            SnapDialogOptions : (options) => SnapDialogOptions(options),
            // You can add more methods here for different types
        };
    }

    

    global.SnapDialog = NsDialog;

})(window);
