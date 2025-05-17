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
const baseUrl = "https://e-blood-donation-system.onrender.com/api/donors";

// Donor form submission handler
document.getElementById('donorForm').addEventListener('submit', function (e) {
  e.preventDefault();

  // Fetch form input values
  const name = document.getElementById("name").value.trim();
  const email = document.getElementById("email").value.trim();
  const bloodGroup = document.getElementById("bloodGroup").value.trim();
  const city = document.getElementById("city").value.trim();
  const phoneNumber = document.getElementById("phoneNumber").value.trim();

  // Construct donor object to match backend fields
  const donor = {
    name: name,
    email: email,
    bloodGroup: bloodGroup,
    location: city,        // Java field: location
    contact: phoneNumber   // Java field: contact
  };

  // Manual validation of each field (match HTML input IDs)
  let valid = true;

  if (!name) {
    document.getElementById("name").style.border = '2px solid red';
    valid = false;
  } else {
    document.getElementById("name").style.border = '1px solid #ccc';
  }

  if (!email) {
    document.getElementById("email").style.border = '2px solid red';
    valid = false;
  } else {
    document.getElementById("email").style.border = '1px solid #ccc';
  }

  if (!bloodGroup) {
    document.getElementById("bloodGroup").style.border = '2px solid red';
    valid = false;
  } else {
    document.getElementById("bloodGroup").style.border = '1px solid #ccc';
  }

  if (!city) {
    document.getElementById("city").style.border = '2px solid red';
    valid = false;
  } else {
    document.getElementById("city").style.border = '1px solid #ccc';
  }

  if (!phoneNumber) {
    document.getElementById("phoneNumber").style.border = '2px solid red';
    valid = false;
  } else {
    document.getElementById("phoneNumber").style.border = '1px solid #ccc';
  }

  if (!valid) {
    alert('Please fill in all required fields.');
    return;
  }

  // Send data to backend
  fetch(baseUrl + "/add", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(donor)
  })
    .then(res => res.json())
    .then(data => {
      alert("Thank you for registering as a donor!");
      document.getElementById("donorForm").reset();
      fetchDonors();
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
          <td>${donor.location}</td>
          <td>${donor.contact}</td>
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