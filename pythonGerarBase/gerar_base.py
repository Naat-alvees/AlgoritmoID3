import numpy as np
import matplotlib.pyplot as plt
from random import randint

limites = [[[0, 12], [2, 60]], [[60, 72], [2, 60]]]

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

x1 = treinamento[0:int(TAMANHO_TREINAMENTO/2), 0]
y1 = treinamento[0:int(TAMANHO_TREINAMENTO/2), 1]
plt.scatter(x1, y1)
plt.show()

x2 = treinamento[int(TAMANHO_TREINAMENTO/2): TAMANHO_TREINAMENTO, 0]
y2 = treinamento[int(TAMANHO_TREINAMENTO/2): TAMANHO_TREINAMENTO, 1]
plt.scatter(x2, y2)
plt.show()


np.savetxt("treinamento.txt", treinamento, fmt="%d")
np.savetxt("teste.txt", teste, fmt="%d")
