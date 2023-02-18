package hk.cand601.booking.exception

class EntityNotFoundException(message: String?): RuntimeException(message)

class BadRequestException(message: String?): RuntimeException(message)

class EntityNotCreatedException(message: String?): RuntimeException(message)

class EntityNotUpdatedException(message: String?): RuntimeException(message)

class ServiceInterruptionException(message: String?): RuntimeException(message)