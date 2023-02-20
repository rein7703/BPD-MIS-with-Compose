const admin = require("firebase-admin")

admin.initializeApp({
  credential: admin.credential.cert("./serviceAccountKey.json"),
  databaseURL: "https://bpd-mis-compose-default-rtdb.asia-southeast1.firebasedatabase.app/"
})

const uid = "obCtcV9PRjSzhEvpwbtmBDPcEZ93"

return admin
  .auth()
  .setCustomUserClaims(uid, { isAdmin: false, isKomisaris : true, isDireksi : false, isDivisi : false, isPemda : false })
  .then(() => {
    // The new custom claims will propagate to the user's ID token the
    // next time a new one is issued.
    console.log(`Komisaris claim added to ${uid}`)
  });