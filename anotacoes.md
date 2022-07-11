# Pegar o GetKeys, só usar o método.
---
Se eu quero gerar o meu par de chave, esse *main* irá gerá-los.
A geração de chaves é o de menos.

Vocês podem usar o código GetKeys, compilar ele e gerar as chaves.
Esse **GetKeys** é só para gerar o par de chaves.
Ele vai passar uma versão atualizada dele.


---
# O que vamos implementar?
Vamos implementar a parte que *cifra* e *descifra* o código.
E que realmente o que crifra e descifra são essas duas linhas ali.

Você pega um arquivo qualquer, por exemplo, ele gerou um arquivo lorem ipsum aí,
tem um arquivo texto que é o lorem ipsum que foi usado para gerar aquele criptografado para
nos.

## O que o meu programa vai fazer?
Ele vai pegar esse arquivo, ele vai ler ele, joga ele numa variável.
VOcê vai pegar isso e codificar ele em *base64*, teoricamente não precisaria codificar ele
mas o HAbib achou melhor fazer assim por questões de acentuação.

O **TextChunk** tem uma classe que lê um arquivo de texto e converte para números.
Para inteiros grandes (BigInteger), ele vai pegar um pedaço de texto e converter
pra BigInteger.

1) Peguei o arquivo de Texto.
2) Converti ele pra Base64.

A gente vai pegar chunks (pedaços) dele.
Esse **TextChunk** já tem implementado.

Então, a gente vai pegar pedaços desse Base64 em formato BigInteger, que vai
ser um numero grande, vamos fazer esse numero elevado à minha chave pública que a gente
gerou com o **GetKeys**, modulo n.
No Java, o BigInteger tem um método chamado modPow(), ele faz esse elevado e faz
o modulo ao mesmo tempo e ai vai dar o resultado,
esse resultado é o que vamos gravar no arquivo.
Gerei o chunk codificado, vou guardar essa linha no arquivo.
E aí você vai pegando chunk por chunk que esse cara vai gerar:
### encodedChunk = originalChunk^e mod n
Terminou isso daí, fechou, fecha o arquivo e terminou.
Isso foi a codificação (encriptação)

---
# Agora é a descriptografia
O que é descriptografia? É você ler o arquivo que tá criptografado, pra cada
linha que você ler já vai ser um numero, então você vai pegar esse cara
elevado à d (chave privada) modulo n, ele vai dar o valor original numerico,
então aí na classe **TextChunk** você vai passar pra ela esse valor numerico e ela
vai gerar o resultado em String.
Aí você vai gerar a String original que era Base64 e você vai concatenar ela,
chunk por chunk.
Terminou de gerar os chunks originais, com o texto concatenado, você faz o Base64
decode, e você vai gerar o texto de origem.


O TextChunk tem um construtor que recebe um valor em String, por exemplo,
quando pegamos o texto base (lorem) você vai jogar ele para o construtor da
**TextChunk**.
A classe TextChunk também tem outro construtor que recebe um BigInteger, daí
quando você passa um BigInteger como parâmetro, aí ele vai fazer um algoritmo
que vai gerar a String do cara que você passou como parâmetro.


dest.txt é o cara CRIPTOGRAFADO, para descriptografar vamos ler o arquivo e usar
aquela equação, jogar o resultado para o TextChunk e ele vai me retornar o valor em String,
e aí cada valor que eu receber vou concatenar ele e gerar o valor que tá em Base64 que logo
vou decodificar.

1. LEr o arquivo de origem
2. converter p/base64
3. Obter chunks do texto em formato BigInteger
4. Codifica com a operação descrita na tarefa


---
A gente cria uma função de Criptografia
A gente pega os três nomes de arquivo

Precisamos carregar o key_file_name
Carregar o text from source_file_name

Carrega a chave o texto.

chunk_size <- get_chunk_size(modulus)

codedText = base64encode(text)

então a gente pegou o chunkSize, a gente codificiou o texto,
então a gente splitou o texto Codificado em varios chunks
e para cada chunk a gente converteu para BigInt


Base64 -> Encriptografia
RSA -> Criptografia


Pseudo-código da atividade:

# Criptografia (RSA)
Function Crypt:

Read key_file (chave pública do RSA)
Read source_file_name (plain text)
Read dest_file_name

### Carrega a chave e o texto
// Carregar estes parâmetros na função
Load keys, modulus from key_file_name
Load text from source_file_name

chunk_size <- get_chunk_size(modulus)

### Primeiro a gente converte pra base64 (encriptografia/codificou)
codexText = base64encode(text)

### Splitamos o texto pelo tamanho do chunk, pra cada chunk que a gente vai fazer
for chunk in codedText.split(by chunk_size) do
  originalChunk = convertToBigInt(chunk)
  encodedChunk = originalChunk.modPow(key, modulus)

  ### Salva o encodedChunk no arquivo de dest, arquivo criptogrado e encriptografado
  save(encodedChunk, dest_file_name)

done


## Para ele descriptografar, vai precisar da chave privada RSA
Function Decrypt:

Read key_file_name (private key)
Read source_file_name (encriptografado)
Read dest_file_name (descriptografado)

Load key, modulus from key_file_name

originalEncodedText = ""

### Li a linha do arquivo, fiz lá o modulo elevado à chave privada, modpow...
for line in source_file_name do
  encodedChunk = get_line_as_big_integer(line)
  originalChunk = encodedChunk.modPow(key, modulus) // encoded^key % modulus

  base64encodedChunk = convert_big_int_to_str(originalChunk)

  originalEncodedText += base64encodedChunk
done

decryptedText = base64decode(originalEncodedText)

save(decryptedText, dest_file_name)