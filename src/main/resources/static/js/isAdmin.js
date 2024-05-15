document.addEventListener("DOMContentLoaded", function() {
    isAdmin();
});

function isAdmin() {
	fetch("/restUser")
		.then(user => user.json())
		.then(data => {
			const auth = document.getElementById("isAdmin");
			if(data.roles.some(role => role.role === "ROLE_ADMIN")) {
			auth.innerHTML = `
			               <a href="/admin" class="list-group-item active waves-effect">
                                    <i class="fa fa-pie-chart mr-3"></i>Admin
                                </a>
			            `;
}
		})
		.catch(error => console.error('Ошибка загрузки данных:', error));

}
