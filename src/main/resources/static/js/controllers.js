//Modulo - Container com varios controllers, etc.
angular.module("sistemaDeMusica", []);
//$scope é um objeto que vai pertencer ao view e ao controller ao msm tempo
angular.module("sistemaDeMusica").controller("sistemaController", function($scope, $http){
	$scope.usuarioLogado;
	$scope.usuarioLogadoStatus = false;
	$scope.artistas = [];
	$scope.artistasFavoritos = [];
	$scope.albuns = [];
	$scope.musicas = []; //Array com todas as musicas do sistema
	$scope.playlists = []; //Array com as playlists do sistema
	$scope.statusCaixaDePesquisaDeArtistas = true;
	$scope.selected; //Variavel usada para se referir ao artista que se está tratando se quiser exibir mais informacoes (US03) - Ver: https://stackoverflow.com/questions/33904251/ng-repeat-does-not-work-in-bootstrap-modal
	
	//Requisicao http feita toda vez que o js for interpretado(toda vez que atualizar a pagina), para carregar os artistas
	$http({method: 'GET', url:'https://musitecasi.herokuapp.com/artistas'})
	.then(function(response){
		$scope.artistas = response.data;
	}, function(response){
		console.log(response.status);
	});

	//popula a tabela de musicas
	$scope.fillMusicas = function(){
		$http({method: 'GET', url:'https://musitecasi.herokuapp.com/musicas'})
		.then(function(response){
			$scope.musicas = response.data;
		}, function(response){
			console.log(response.status);
		});		
	}
	$scope.fillMusicas(); //Atualiza as musicas pegando as que estao no bd. Só pode chamar depois que definir
	

	$scope.cadastrarUsuario = function(usuario){
		$http({method:'POST', url:'https://musitecasi.herokuapp.com/usuarios/cadastro', data: usuario})
		.then(function(response){
			alert("Usuario cadastrado com sucesso!");
			delete $scope.usuarioParaCadastrar;
		}, function(response){
			alert("Não foi possivel cadastrar com as informações dadas!");
		});
	}

	$scope.logarUsuario = function(usuario){
		$http({method:'POST', url: 'https://musitecasi.herokuapp.com/usuarios/login', data: usuario})
		.then(function(response){
			alert("Usuario logado com sucesso!");
			delete $scope.usuarioParaLogar;
			$scope.usuarioLogado = response.data;
			console.log($scope.usuarioLogado);
			$scope.usuarioLogadoStatus = true;
			$scope.fillArtistasFavoritos(); //Toda vez que logar, ja carrega o array com artistas favoritos do banco de dados
			$scope.fillPlaylists();
		}, function(response){
			alert("Não foi possivel logar no sistema, tente novamente!")
		});
	}
	
	//Adiciona um artista na colecao de favoritos de um usuario ja logado
	$scope.adicionaArtistaFavorito = function(favorito){
		if(!$scope.usuarioLogadoStatus){
			alert("Você precisa estar logado para realizar essa ação!")
		}else{
			$http({method:'POST', url: 'https://musitecasi.herokuapp.com/usuarios/favoritos/' + $scope.usuarioLogado.id, data: favorito})
			.then(function(response){
				alert("Artista adicionado na sua coleção de artistas favoritos com sucesso!");
				$scope.fillArtistasFavoritos();
			}, function(response){
				alert("Não foi possivel adicionar o artista na sua coleção de artistas favoritos!");
			});
		}
	}
	
	$scope.excluirArtistaFavorito = function(idUsuario, idFavorito){
		$http({method: 'DELETE', url: 'https://musitecasi.herokuapp.com/usuarios/favoritos/' + idUsuario + "/" + idFavorito})
		.then(function(response){
			$scope.fillArtistasFavoritos(); //Atualiza o array de artistas favoritos, ja que mexeu no bd
			alert("Artista excluído da sua coleção de artistas favoritos com sucesso!");
			}, function(response){
			alert("Não foi possível excluir o artista da sua coleção de artistas favoritos!");	
			});
		}
	
	//Carrega/enche/atualiza o array que tem os artistas favoritos de um usuario
	$scope.fillArtistasFavoritos = function(){
		if($scope.usuarioLogadoStatus){
			$http({method:'GET', url: 'https://musitecasi.herokuapp.com/usuarios/favoritos/'+$scope.usuarioLogado.id})
			.then(function(response){
				$scope.artistasFavoritos = response.data;
				$scope.usuarioLogado.favoritos = response.data;
				console.log($scope.usuarioLogado);
				console.log($scope.artistasFavoritos);
			}, function(response){
				$scope.artistasFavoritos = [];
			});
		}
	}


	//Metodo usado para atualizar o select
	$scope.select = function(artista){
		$scope.selected = artista;
	}


	$scope.changeStatusCaixaPesquisa = function(){ //Usado no botão de pesquisar artistas, deixar visivel caixa de pesquisa
		$scope.statusCaixaDePesquisaDeArtistas = !$scope.statusCaixaDePesquisaDeArtistas;
	}

	//EXCLUIR NO FINAL
	$scope.adicionarArtista = function(artista){
		if(existeArtista(artista)){
			alert("Artista já existente no sistema");
		}else{
			artista.favorito = false; //propriedade q indica se um artista é favorito ou nao. Sempre adiciona um artista nao favorito
			if(artista.imagem === undefined){ //Se o artista nao tiver imagem
				artista.imagem = "img/interrogacao.jp";
			}
			$scope.artistas.push(angular.copy(artista)); //Cria uma copia do objeto artista e ela que sera adicionada ao array
			delete $scope.artista; //Deleta o artista que esta no $scope, a copia ja foi add no array
		}
	}

	$scope.addArtista = function(artista){
		if(existeArtista(artista)){
			alert("Artista já existente no sistema");
		}else{
			$http({method: 'POST', url: 'http://musitecasi.herokuapp.com/artistas', data: artista})
			.then(function(response){ //callback de sucesso
				$scope.artistas.push(response.data); //Pois nao e atualizado com o bd automaticamente. Adicionar o artista que é o retorno da requisicao(este ja tem id)
				delete $scope.artista; //Para se quando for mexer no campo, nao ficar modificando um artista q ja esta na tabela

			}, function(response){ //callback de "insucesso"
				alert("Não foi possível adicionar o artista no sistema");
			});
		}
	}
	
	$scope.addPlaylist = function(playlist){
		if(!$scope.usuarioLogadoStatus){
			alert("Você precisa estar logado para realizar esta ação!")
		}else{
			$http({method: 'POST',	 url:'http://musitecasi.herokuapp.com/usuarios/playlist/' + $scope.usuarioLogado.nome, data: playlist})
			.then(function(response){
				delete $scope.playlist;
				$scope.fillPlaylists();
				alert("Playlist adicionada com sucesso!");
			}, function(response){
				alert("Não foi possível adicionar a playlist ao sistema!");
			});
		}
		
	}
	
	$scope.fillPlaylists = function(){
		if($scope.usuarioLogadoStatus){
			$http({method: 'GET', url:'https://musitecasi.herokuapp.com/usuarios/playlist/' + $scope.usuarioLogado.nome})
			.then(function(response){
				$scope.playlists = response.data;
				$scope.usuarioLogado.playlists = response.data;
				console.log($scope.usuarioLogado);
			}, function(response){
				
			});
		}
	}
	
	$scope.addMusicaEmPlaylist = function(nomeDaPlaylist, musica){
		if(!$scope.usuarioLogadoStatus){
			alert("É preciso estar logado para realizar esta ação!")
		}else{
			$http({method: 'POST', url:'https://musitecasi.herokuapp.com/usuarios/playlist/' + $scope.usuarioLogado.nome + '/' + nomeDaPlaylist, data: musica})
			.then(function(response){
				alert("Musica adicionada na playlist com sucesso!");
				$scope.fillPlaylists(); //Atualiza as playlists
			}, function(response){
				alert("Não foi possível adicionar a música na playlist!");
			});
		}
	}

	//funcao que retorna se o array esta populado ou nao
	$scope.isPopulated = function(array){
		return array.length > 0;
	}

	//Verifica se existe artista no array de artistas
	existeArtista = function(artista){
		for(i = 0; i < $scope.artistas.length; i++){
			if($scope.artistas[i].nome === artista.nome){
				return true;
			}
		}
		return false;
	}



	//Atualizar artista - Ultima musica que o usuario ouviu e nota
	$scope.atualizaArtista = function(selectedItem){
		for(i = 0; i < $scope.artistas.length; i++){
			if($scope.artistas[i].nome == selectedItem.nome){
				$scope.artistas[i].nota = selectedItem.nota;
				$scope.artistas[i].ultimaMusica = selectedItem.ultimaMusica;
			}
		}
	}

	//Add artista aos favoritos
	$scope.adicionarAosFavoritos = function(artista){
		if(!artista.favorito){
			artista.favorito = true;
			$scope.artistasFavoritos.push(artista);
		}
	}

	$scope.removerDosFavoritos = function(artista){
		for(i = 0; i < $scope.artistasFavoritos.length; i++){
			if(artista.nome == $scope.artistasFavoritos[i].nome){
				$scope.artistasFavoritos.splice(i, 1); //remove 1 elemento do indice i
			}
		}
		artista.favorito = false;
	}





	//Verifica se existem artistas favoritos no sistema. -- Nao usada mais - excluir
	$scope.existeArtistasFavoritos = function(){
		for(i = 0; i < $scope.artistas.length; i++){
			if($scope.artistas[i].favorito == true){
				return true;
			}
		}
		return false;
	}

	//Criacao do tipo Playlist
	function Playlist(nome){
		this.nome = nome;
		this.musicasDaPlaylist = [];

		this.adicionaMusica = function(musica){
			this.musicasDaPlaylist.push(musica);
		}

		//retorna array de musicas da playlist
		this.retornaMusicas = function(){
			return this.musicasDaPlaylist;
		}
	}

	//Adiciona uma musica em uma playlist se ela nao tiver uma musica com o msm nome
	$scope.adicionaMusicaNaPlaylist = function(musica, nomeDaPlaylist){
		if(!$scope.containsPlaylist(nomeDaPlaylist)){
			alert("Não existe essa playlist no sistema");
		}else{
			for(var i = 0; i < $scope.playlists.length; i++){
				if($scope.playlists[i].nome == nomeDaPlaylist){
					if(containsMusica($scope.playlists[i].retornaMusicas(), musica.nome)){
						alert("A playlist já tem uma música com esse nome!");
					}else{
						$scope.playlists[i].adicionaMusica(musica);
						alert("Musica adicionada na playlist com sucesso!");
					}
				}
			}
		}
	}


	//Adiciona playlist no sistema
	$scope.adicionaPlaylist = function(nome){
		if(!$scope.containsPlaylist(nome)){
			$scope.playlists.push(new Playlist(nome));
		}else{
			alert("Já existe uma playlist com o mesmo nome no sistema!")
		}


	}

	//Verifica se uma playlist com o mesmo nome existe no sistema
	$scope.containsPlaylist = function(nome){
		for(i = 0; i < $scope.playlists.length; i++){
			if($scope.playlists[i].nome == nome){
				return true;
			}
		}
		return false;
	}

	//Excluir uma playlist dada a playlist
	$scope.excluirPlaylist = function(playlist){
		for (var i = 0; i < $scope.playlists.length; i++){
			if($scope.playlists[i].nome == playlist.nome){
				$scope.playlists.splice(i, 1);
				alert("Playlist excluida com sucesso.")
			}
		}
	}

	 //Excluir uma musica de uma playlist dada a musica
	$scope.excluirMusicaDaPlaylist = function(playlist, musica){
		for(var i = 0; i < playlist.retornaMusicas().length; i++){
			if(playlist.retornaMusicas()[i].nome == musica.nome){
				playlist.retornaMusicas().splice(i, 1);
			}
		}
	}




	//Criacao do tipo album
	function Album(nomeDoAlbum, autorDoAlbum){
		this.musicas = []; //Array de musicas
		this.nomeDoAlbum = nomeDoAlbum;
		this.autorDoAlbum = autorDoAlbum;

		this.pushMusic = function(musica){//Add musica ao album, o album NAO pode ter 2 musicas com o mesmo nome
			if(!containsMusica(this.musicas, musica.nome)){
				this.musicas.push(musica);
				$scope.musicas.push(musica);
				alert("Música adicionada com sucesso")
			}else{
				alert("Musica ja existente no álbum");
			}
		}
	}


	//Metodo que verifica se existe uma musica em um dado array de musicas
	containsMusica = function(arrayMusica, nomeDaMusica){
		for(i = 0; i < arrayMusica.length; i++){
			if(arrayMusica[i].nome == nomeDaMusica){
				return true;
			}
		}
		return false;
	}



	//Verifica se existe um determinado album com determinado nome, se existir, retorna o album, se nao, retorna um novo album vazio
	retornaAlbum = function(nomeDoAlbum, autor){ //Parametro autor (do album) usado somente se for necessario criar um novo album
		for(i = 0; i < $scope.albuns.length; i++){
			if($scope.albuns[i].nomeDoAlbum == nomeDoAlbum){
				return $scope.albuns[i];
			}
		}
		var novoAlbum = new Album(nomeDoAlbum, autor);
		$scope.albuns.push(novoAlbum);
		return novoAlbum;
	}


	//DEVE SER EXCLUIDO
	//Adiciona uma musica a um album que está no array de albuns.
	$scope.adicionaMusica = function(musica){
		album = retornaAlbum(musica.album, musica.autor);
		album.pushMusic(angular.copy(musica));
		delete musica;
	}

	$scope.addMusica = function(musica){
		if(containsMusica($scope.musicas, musica.nome)){
			alert("Musica ja existente no sistema")
		}else{
			$http({method:'POST', url:'https://musitecasi.herokuapp.com/musicas', data: musica})
			.then(function(response){
				$scope.fillMusicas();
				delete $scope.musica;
			}, function(response){
				alert("Não foi possivel adicionar a musica no sistema")
				console.log(response.status)
			});
		}
	}

	//retorna um array de albuns de um determinado autor dado no parametro
	$scope.retornaAlbuns = function(autor){
		retorno = [];
		for(i = 0; i < $scope.albuns.length; i++){
			if($scope.albuns[i].autorDoAlbum == autor){
				retorno.push($scope.albuns[i]);
			}
		}
		return retorno;
	}






});
