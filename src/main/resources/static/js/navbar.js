document.addEventListener("DOMContentLoaded", function() {
    authority();
});

function authority() {
	fetch("/restUser")
		.then(user => user.json())
		.then(data => {
			const auth = document.getElementById("authUser");
			auth.innerHTML = `
			            <span class="text-white bg-dark font-weight-bold">${data.email} </span>
			            <span class="text-white bg-dark"> with roles: </span>
			            <span class="text-white bg-dark"> ${data.roles.map(role => role.role).join(', ')} </span>
			            `;

		})
		.catch(error => console.error('Ошибка загрузки данных:', error));

}


