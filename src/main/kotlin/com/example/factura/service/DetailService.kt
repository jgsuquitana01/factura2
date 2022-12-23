package com.example.factura.service

import com.example.factura.model.Detail
import com.example.factura.model.Product
import com.example.factura.repository.DetailRepository
import com.example.factura.repository.InvoiceRepository
import com.example.factura.repository.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException


@Service
class DetailService {
    @Autowired
    lateinit var detailRepository: DetailRepository
    @Autowired
    lateinit var invoiceRepository: InvoiceRepository
    @Autowired
    lateinit var productRepository:ProductRepository


    fun list ():List<Detail>{
        return detailRepository.findAll()
    }

    fun save (detail: Detail):Detail{
        try{
            invoiceRepository.findById(detail.invoiceId)
                ?:throw Exception("El id ${detail.invoiceId} de factura no existe")
            val response = detailRepository.save(detail)
            productRepository.findById(detail.productId)
                ?:throw Exception("El id ${detail.productId} de detalle no existe")
            val actualizateStockProduct = productRepository.findById(response.productId)
            actualizateStockProduct?.apply{
                stock = stock?.minus(detail.quantity!!)
            }
            productRepository.save(actualizateStockProduct!!)
            return detailRepository.save(detail)
        }catch (ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }

    }

    fun update(detail: Detail):Detail{
        try {
            detailRepository.findById(detail.id)
                ?: throw Exception("El id ${detail.id} en detalle no existe")
            return detailRepository.save(detail)
        }
        catch(ex:Exception){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)
        }
    }

    fun updateQuantity(detail: Detail):Detail{
        try{
            val response = detailRepository.findById(detail.id)
                ?:throw Exception("El ${detail.id} en detalle no existe")
            response.apply{
                quantity = detail.quantity
            }
            return detailRepository.save(response)
        }
        catch (ex:Exception){
            throw  ResponseStatusException(HttpStatus.NOT_FOUND, ex.message)

        }
    }
}

