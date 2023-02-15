/* Navbar and Sidebar */
fetch('../../../templates/backend-views/duwa-admin/navbar.html')
.then(res => res.text())
.then(text => {
    let oldelem = document.querySelector("script#replace_with_navbar");
    let newelem = document.createElement("div");
    newelem.innerHTML = text;
    oldelem.parentNode.replaceChild(newelem,oldelem);
})

fetch('../../../templates/backend-views/duwa-admin/sidebar.html')
.then(res => res.text())
.then(text => {
    let oldelem = document.querySelector("script#replace_with_sidebar");
    let newelem = document.createElement("div");
    newelem.innerHTML = text;
    oldelem.parentNode.replaceChild(newelem,oldelem);
})

/*End of Navbar and Sidebar*/

/* Content */
fetch('../../../templates/backend-views/duwa-admin/content.html')
.then(res => res.text())
.then(text => {
    let oldelem = document.querySelector("script#replace_with_content");
    let newelem = document.createElement("div");
    newelem.innerHTML = text;
    oldelem.parentNode.replaceChild(newelem,oldelem);
})
/* End of Content */


 