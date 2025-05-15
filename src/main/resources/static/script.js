// Smooth Scroll for navigation links
document.querySelectorAll('.nav-links a').forEach(link => {
  link.addEventListener('click', function (e) {
    e.preventDefault();
    const targetId = this.getAttribute('href').substring(1);
    const targetSection = document.getElementById(targetId);
    if (targetSection) {
      targetSection.scrollIntoView({ behavior: 'smooth' });
    }
  });
});

// Base URL of your Spring Boot backend
const baseUrl = "http://localhost:8080/api/donors";

// Donor form submission handler
document.getElementById('donorForm').addEventListener('submit', function (e) {
  e.preventDefault();

  // Fetching form data
  const donor = {
    name: document.getElementById("name").value.trim(),
    bloodGroup: document.getElementById("bloodGroup").value.trim(),
    city: document.getElementById("city").value.trim(),
    phoneNumber: document.getElementById("phoneNumber").value.trim()
  };

  // Validation
  let valid = true;
  for (let key in donor) {
    if (!donor[key]) {
      valid = false;
      document.getElementById(key).style.border = '2px solid red';
    } else {
      document.getElementById(key).style.border = '1px solid #ccc';
    }
  }

  if (!valid) {
    alert('Please fill in all required fields.');
    return;
  }

  // API Call to register donor
  fetch(baseUrl + "/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(donor)
  })
    .then(res => res.json())
    .then(data => {
      alert("Thank you for registering as a donor!");
      document.getElementById("donorForm").reset();
      fetchDonors(); // Refresh donor list
    })
    .catch(error => {
      console.error("Error:", error);
      alert("Failed to register donor.");
    });
});

// Fetch all donors and populate the table
function fetchDonors() {
  fetch(baseUrl + "/all")
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById("donorTableBody");
      tbody.innerHTML = "";

      data.forEach(donor => {
        const row = `<tr>
          <td>${donor.id}</td>
          <td>${donor.name}</td>
          <td>${donor.bloodGroup}</td>
          <td>${donor.city}</td>
          <td>${donor.phoneNumber}</td>
          <td><button onclick="deleteDonor(${donor.id})">Delete</button></td>
        </tr>`;
        tbody.innerHTML += row;
      });
    });
}

// Delete a donor
function deleteDonor(id) {
  fetch(baseUrl + "/delete/" + id, {
    method: "DELETE"
  })
    .then(() => {
      alert("Donor deleted.");
      fetchDonors();
    })
    .catch(error => {
      console.error("Delete failed:", error);
      alert("Failed to delete donor.");
    });
}
