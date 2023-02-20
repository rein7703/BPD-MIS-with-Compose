const admin = require("firebase-admin")

admin.initializeApp({
  credential: admin.credential.cert("./serviceAccountKey.json"),
  databaseURL: "https://bpd-mis-compose-default-rtdb.asia-southeast1.firebasedatabase.app/"
})

const uid = "z5DpgFbtTcfcpdXBGKVgvjfigGm2"

return admin
  .auth()
  .setCustomUserClaims(uid, { isAdmin: false, isKomisaris : false, isDireksi : false, isDivisi : false, isPemda : true })
  .then(() => {
    // The new custom claims will propagate to the user's ID token the
    // next time a new one is issued.
    console.log(`Pemda claim added to ${uid}`)
  });