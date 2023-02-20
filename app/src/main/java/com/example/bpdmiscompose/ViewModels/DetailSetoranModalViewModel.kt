package com.example.bpdmiscompose.ViewModels

/*
class DetailSetoranModalViewModel (
    private val repository: SetoranModalRepository
    ):ViewModel(){
    var detailSetoranModalUiState by mutableStateOf(DetailSetoranModalUiState())
        private set

    private val hasUser : Boolean
        get() = repository.hasUser()

    private val user:FirebaseUser?
        get() = repository.user
    fun onPemdaIdChange(pemdaId: String) {
        detailSetoranModalUiState = detailSetoranModalUiState.copy(pemdaId = pemdaId)
    }
    fun onModalDisetorRUPSChange(modalDisetorRUPS: Int) {
        detailSetoranModalUiState = detailSetoranModalUiState.copy(modalDisetorRUPS = modalDisetorRUPS)
    }
    fun onKomposisiRUPSChange(komposisiRUPS: Int) {
        detailSetoranModalUiState = detailSetoranModalUiState.copy(komposisiRUPS = komposisiRUPS)
    }
    fun onRealisasiDanaSetoranModalChange(realisasiDanaSetoranModal: Int) {
        detailSetoranModalUiState = detailSetoranModalUiState.copy(realisasiDanaSetoranModal = realisasiDanaSetoranModal)
    }
    fun onTotalModalDesemberChange(totalModalDesember: Int) {
        detailSetoranModalUiState = detailSetoranModalUiState.copy(totalModalDesember = totalModalDesember)
    }
    fun onTahunChange(tahun: Int) {
        detailSetoranModalUiState = detailSetoranModalUiState.copy(tahun = tahun)
    }

    fun addSetoran(){
        if(hasUser){
            repository.addSetoran(
                userId = user!!.uid,
                pemdaId = detailSetoranModalUiState.pemdaId,
                modalDisetorRUPS = detailSetoranModalUiState.modalDisetorRUPS,
                komposisiRUPS = detailSetoranModalUiState.komposisiRUPS,
                realisasiDanaSetoranModal = detailSetoranModalUiState.realisasiDanaSetoranModal,
                totalModalDesember = detailSetoranModalUiState.totalModalDesember,
                tahun = detailSetoranModalUiState.tahun,
                timestamp = Timestamp.now()
            ){
                detailSetoranModalUiState = detailSetoranModalUiState.copy(setoranAddedStatus = it)
            }
        }
    }

    fun setEditFields(setoranModal: SetoranModal){
        detailSetoranModalUiState = detailSetoranModalUiState.copy(
            pemdaId = setoranModal.pemdaId,
            modalDisetorRUPS = setoranModal.modalDisetorRUPS,
            komposisiRUPS = setoranModal.komposisiRUPS,
            realisasiDanaSetoranModal = setoranModal.realisasiDanaSetoranModal,
            totalModalDesember = setoranModal.totalModalDesember,
            tahun = setoranModal.tahun,
            selectedSetoran = setoranModal
        )
    }

    fun getSingleSetoran(setoranId: String){
        repository.getSingleSetoran(setoranId = setoranId){
            detailSetoranModalUiState = detailSetoranModalUiState.copy(selectedSetoran = it)
            detailSetoranModalUiState.selectedSetoran?.let{it1 -> setEditFields(it1)}
        }
    }

    fun updateSetoran(
        setoranId:String
    ){
        repository.updateSetoran(
            setoranId = setoranId,
            pemdaId = detailSetoranModalUiState.pemdaId,
            modalDisetorRUPS = detailSetoranModalUiState.modalDisetorRUPS,
            komposisiRUPS = detailSetoranModalUiState.komposisiRUPS,
            realisasiDanaSetoranModal = detailSetoranModalUiState.realisasiDanaSetoranModal,
            totalModalDesember = detailSetoranModalUiState.totalModalDesember,
            tahun = detailSetoranModalUiState.tahun,
            timestamp = Timestamp.now()
        ){
            detailSetoranModalUiState = detailSetoranModalUiState.copy(setoranUpdateStatus = it)
        }

        fun resetSetooranAddedStatus(){
            detailSetoranModalUiState = detailSetoranModalUiState.copy(setoranAddedStatus = false)
        }

        fun resetState(){
            detailSetoranModalUiState = DetailSetoranModalUiState()
        }
    }
}

data class DetailSetoranModalUiState(
    val pemdaId : String = "",
    val modalDisetorRUPS : Int = 0,
    val komposisiRUPS : Int = 0,
    val realisasiDanaSetoranModal : Int = 0,
    val totalModalDesember : Int = 0,
    val tahun : Int = 0,
    val setoranAddedStatus : Boolean = false,
    val setoranUpdateStatus: Boolean = false,
    val selectedSetoran : SetoranModal? = null
)

 */