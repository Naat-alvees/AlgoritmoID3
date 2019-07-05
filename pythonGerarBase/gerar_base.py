import numpy as np
from random import randint

limites = [[[0, 4], [2, 6]], [[6, 10], [2, 6]]]

TAMANHO_TREINAMENTO = 100
TAMANHO_TESTE = 20


def gera_dados(quantidade):
    dados = []
    for i in range(2):
        for j in range(int(quantidade/2)):
            dado = [randint(limites[i][0][0], limites[i][0][1]), randint(limites[i][1][0], limites[i][1][1]), i]
            dados.append(dado)
    return np.array(dados)


treinamento = gera_dados(TAMANHO_TREINAMENTO)
teste = gera_dados(TAMANHO_TESTE)

np.savetxt("treinamento.txt", treinamento, fmt="%d")
np.savetxt("teste.txt", teste, fmt="%d")
