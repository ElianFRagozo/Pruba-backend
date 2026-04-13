variable "aws_region" {
  description = "Region de AWS para desplegar la infraestructura"
  type        = string
  default     = "us-east-1"
}

variable "project_name" {
  description = "Nombre base del proyecto"
  type        = string
  default     = "franquicias-api"
}

variable "container_image" {
  description = "Imagen completa del contenedor. Si queda vacio, usara ECR con tag latest"
  type        = string
  default     = ""
}

variable "container_port" {
  description = "Puerto expuesto por el contenedor"
  type        = number
  default     = 8080
}

variable "desired_count" {
  description = "Cantidad de tareas Fargate"
  type        = number
  default     = 1
}

variable "task_cpu" {
  description = "CPU para la tarea ECS"
  type        = number
  default     = 512
}

variable "task_memory" {
  description = "Memoria para la tarea ECS"
  type        = number
  default     = 1024
}

variable "mongodb_uri" {
  description = "URI de MongoDB (Atlas, externa o DocumentDB)"
  type        = string
  sensitive   = true
  default     = ""
}

variable "provision_documentdb" {
  description = "Si es true, aprovisiona DocumentDB basico"
  type        = bool
  default     = false
}

variable "documentdb_username" {
  description = "Usuario admin de DocumentDB"
  type        = string
  default     = "franquiciasadmin"
}

variable "documentdb_password" {
  description = "Password admin de DocumentDB"
  type        = string
  sensitive   = true
  default     = "ChangeMe123!"
}
