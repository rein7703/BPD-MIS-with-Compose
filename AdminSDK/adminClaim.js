const admin = require("firebase-admin")

admin.initializeApp({
  credential: admin.credential.cert("./serviceAccountKey.json"),
  databaseURL: "https://bpd-mis-compose-default-rtdb.asia-southeast1.firebasedatabase.app/"
})

const uid = "wkwkwkw"

return admin
  .auth()
  .setCustomUserClaims(uid, { isAdmin: true, isKomisaris : false, isDireksi : false, isDivisi : false, isPemda : false })
  .then(() => {
    // The new custom claims will propagate to the user's ID token the
    // next time a new one is issued.
    console.log(`Admin claim added to ${uid}`)
  })
  .catch((error) => {
    console.log('Error Creating Custom User Claims: ', error);
  });