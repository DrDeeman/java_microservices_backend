var product_users;(()=>{"use strict";var e,r,t,n,o={},a={};function i(e){var r=a[e];if(void 0!==r)return r.exports;var t=a[e]={id:e,exports:{}};return o[e](t,t.exports,i),t.exports}i.m=o,i.n=e=>{var r=e&&e.__esModule?()=>e.default:()=>e;return i.d(r,{a:r}),r},i.d=(e,r)=>{for(var t in r)i.o(r,t)&&!i.o(e,t)&&Object.defineProperty(e,t,{enumerable:!0,get:r[t]})},i.f={},i.e=e=>Promise.all(Object.keys(i.f).reduce(((r,t)=>(i.f[t](e,r),r)),[])),i.u=e=>e+".js",i.o=(e,r)=>Object.prototype.hasOwnProperty.call(e,r),e={},r="microFrontend_product_users:",i.l=(t,n,o,a)=>{if(e[t])e[t].push(n);else{var s,u;if(void 0!==o)for(var d=document.getElementsByTagName("script"),l=0;l<d.length;l++){var f=d[l];if(f.getAttribute("src")==t||f.getAttribute("data-webpack")==r+o){s=f;break}}s||(u=!0,(s=document.createElement("script")).charset="utf-8",s.timeout=120,i.nc&&s.setAttribute("nonce",i.nc),s.setAttribute("data-webpack",r+o),s.src=t),e[t]=[n];var p=(r,n)=>{s.onerror=s.onload=null,clearTimeout(c);var o=e[t];if(delete e[t],s.parentNode&&s.parentNode.removeChild(s),o&&o.forEach((e=>e(n))),r)return r(n)},c=setTimeout(p.bind(null,void 0,{type:"timeout",target:s}),12e4);s.onerror=p.bind(null,s.onerror),s.onload=p.bind(null,s.onload),u&&document.head.appendChild(s)}},i.r=e=>{"undefined"!=typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(e,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(e,"__esModule",{value:!0})},t={261:[72]},n={72:["default","./Users",915]},i.f.remotes=(e,r)=>{i.o(t,e)&&t[e].forEach((e=>{var t=i.R;t||(t=[]);var o=n[e];if(!(t.indexOf(o)>=0)){if(t.push(o),o.p)return r.push(o.p);var a=r=>{r||(r=new Error("Container missing")),"string"==typeof r.message&&(r.message+='\nwhile loading "'+o[1]+'" from '+o[2]),i.m[e]=()=>{throw r},o.p=0},s=(e,t,n,i,s,u)=>{try{var d=e(t,n);if(!d||!d.then)return s(d,i,u);var l=d.then((e=>s(e,i)),a);if(!u)return l;r.push(o.p=l)}catch(e){a(e)}},u=(e,r,n)=>s(r.get,o[1],t,0,d,n),d=r=>{o.p=1,i.m[e]=e=>{e.exports=r()}};s(i,o[2],0,0,((e,r,t)=>e?s(i.I,o[0],0,e,u,t):a()),1)}}))},(()=>{i.S={};var e={},r={};i.I=(t,n)=>{n||(n=[]);var o=r[t];if(o||(o=r[t]={}),!(n.indexOf(o)>=0)){if(n.push(o),e[t])return e[t];i.o(i.S,t)||(i.S[t]={}),i.S[t];var a=[];return"default"===t&&(e=>{var r=e=>{return r="Initialization of sharing external failed: "+e,void("undefined"!=typeof console&&console.warn&&console.warn(r));var r};try{var o=i(915);if(!o)return;var s=e=>e&&e.init&&e.init(i.S[t],n);if(o.then)return a.push(o.then(s,r));var u=s(o);if(u&&u.then)return a.push(u.catch(r))}catch(e){r(e)}})(),a.length?e[t]=Promise.all(a).then((()=>e[t]=1)):e[t]=1}}})(),i.p="http://127.0.0.1:8050/products_api/",(()=>{var e={256:0};i.f.j=(r,t)=>{var n=i.o(e,r)?e[r]:void 0;if(0!==n)if(n)t.push(n[2]);else{var o=new Promise(((t,o)=>n=e[r]=[t,o]));t.push(n[2]=o);var a=i.p+i.u(r),s=new Error;i.l(a,(t=>{if(i.o(e,r)&&(0!==(n=e[r])&&(e[r]=void 0),n)){var o=t&&("load"===t.type?"missing":t.type),a=t&&t.target&&t.target.src;s.message="Loading chunk "+r+" failed.\n("+o+": "+a+")",s.name="ChunkLoadError",s.type=o,s.request=a,n[1](s)}}),"chunk-"+r,r)}};var r=(r,t)=>{var n,o,[a,s,u]=t,d=0;if(a.some((r=>0!==e[r]))){for(n in s)i.o(s,n)&&(i.m[n]=s[n]);u&&u(i)}for(r&&r(t);d<a.length;d++)o=a[d],i.o(e,o)&&e[o]&&e[o][0](),e[o]=0},t=self.webpackChunkmicroFrontend_product_users=self.webpackChunkmicroFrontend_product_users||[];t.forEach(r.bind(null,0)),t.push=r.bind(null,t.push.bind(t))})(),i.nc=void 0;var s,u,d,l,f={};s=f,u={"./ProductUsers":()=>i.e(261).then((()=>()=>i(282)))},d=(e,r)=>(i.R=r,r=i.o(u,e)?u[e]():Promise.resolve().then((()=>{throw new Error('Module "'+e+'" does not exist in container.')})),i.R=void 0,r),l=(e,r)=>{if(i.S){var t="default",n=i.S[t];if(n&&n!==e)throw new Error("Container initialization failed as it has already been initialized with a different share scope");return i.S[t]=e,i.I(t,r)}},i.d(s,{get:()=>d,init:()=>l}),product_users=f})();