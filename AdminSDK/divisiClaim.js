const admin = require("firebase-admin")

admin.initializeApp({
  credential: admin.credential.cert("./serviceAccountKey.json"),
  databaseURL: "https://bpd-mis-compose-default-rtdb.asia-southeast1.firebasedatabase.app/"
})

const uid = "F0d3XKVx42aWqsw7FDBIAbd7LZA2"

return admin
  .auth()
  .setCustomUserClaims(uid, { isAdmin: false, isKomisaris : false, isDireksi : false, isDivisi : true, isPemda : false })
  .then(() => {
    // The new custom claims will propagate to the user's ID token the
    // next time a new one is issued.
    console.log(`Divisi claim added to ${uid}`)
  });