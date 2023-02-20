package com.example.bpdmiscompose.components

/*
@Composable
fun DetailSetoranModalLayout(
    detailSetoranModalViewModel: DetailSetoranModalViewModel?,
    setoranId:String,
    onNavigate:() -> Unit = {},
){
    val detailSetoranModalUiState = detailSetoranModalViewModel?.detailSetoranModalUiState?: DetailSetoranModalUiState()
    val isFormsNotBlank : Boolean = detailSetoranModalUiState.pemdaId.isNotBlank() &&
            detailSetoranModalUiState.modalDisetorRUPS != 0 &&
            detailSetoranModalUiState.komposisiRUPS != 0 &&
            detailSetoranModalUiState.realisasiDanaSetoranModal != 0 &&
            detailSetoranModalUiState.totalModalDesember != 0 &&
            detailSetoranModalUiState.tahun != 0
    val isSetoranModalIdNotBlank = setoranId.isNotBlank()
    val icon = if (isFormsNotBlank && isSetoranModalIdNotBlank) Icons.Default.Refresh else Icons.Default.Check
    LaunchedEffect(Unit){
        if(isSetoranModalIdNotBlank){
            detailSetoranModalViewModel?.getSingleSetoran(setoranId)
        }
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
    ) {

    }
}

 */