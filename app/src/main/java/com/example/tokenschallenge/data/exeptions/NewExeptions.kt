package com.example.tokenschallenge.data.exeptions


class UnauthorizedException : Exception("Check your password and your phone number")

class ConflictException : Exception("There is a conflict")

class PayloadTooLargeException : Exception("Payload too large")

class ServerException : Exception("not me it's a server error")
