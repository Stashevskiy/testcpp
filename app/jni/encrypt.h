#pragma once
#ifndef ENCRYPT_H
#define ENCRYPT_H
#include <stdlib.h>
#include <string.h>
#include "blake2b.h"
#include "rijndael-alg-fst.h"

typedef unsigned char byte;
int crypto_aead_decrypt(
	unsigned char* m, unsigned long long* mlen,
	unsigned char* nsec,
	const unsigned char* c, unsigned long long clen,
	const unsigned char* ad, unsigned long long adlen,
	const unsigned char* npub,
	const unsigned char* k
);
int crypto_aead_encrypt(
	unsigned char* c, unsigned long long* clen,
	const unsigned char* m, unsigned long long mlen,
	const unsigned char* ad, unsigned long long adlen,
	const unsigned char* nsec,
	const unsigned char* npub,
	const unsigned char* k
);
void Encrypt(byte* K, unsigned kbytes,
	byte* N, unsigned nbytes,
	byte* AD[], unsigned adbytes[],
	unsigned veclen, unsigned abytes,
	byte* M, unsigned mbytes, byte* C);

int Decrypt(byte* K, unsigned kbytes,
	byte* N, unsigned nbytes,
	byte* AD[], unsigned adbytes[],
	unsigned veclen, unsigned abytes,
	byte* C, unsigned cbytes, byte* M);

#endif