output "ecr_repository_url" {
  description = "URL del repositorio ECR para publicar la imagen"
  value       = aws_ecr_repository.app.repository_url
}

output "alb_dns_name" {
  description = "DNS publico del balanceador"
  value       = aws_lb.app.dns_name
}

output "ecs_cluster_name" {
  description = "Nombre del cluster ECS"
  value       = aws_ecs_cluster.app.name
}

output "documentdb_endpoint" {
  description = "Endpoint de DocumentDB si se aprovisiona"
  value       = var.provision_documentdb ? aws_docdb_cluster.mongo[0].endpoint : null
}
