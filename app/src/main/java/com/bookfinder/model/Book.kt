package com.bookfinder.model

import com.bookfinder.constants.Constants

/**
 * Google Book api를 통해 받은 응답 모델
 */
object Book {
    class RS(var kind: String, var totalItems: String, var items: ArrayList<Items>) {
        open class Items(
            var kind: String,
            var id: String,
            var etag: String,
            var selfLink: String,
            var volumeInfo: VolumeInfo,
            var saleInfo: SaleInfo,
            var accessInfo: AccessInfo,
            var searchInfo: SearchInfo
        ) : ViewTypeModel() {
            class VolumeInfo(
                var title: String,
                var subtitle: String,
                var authors: ArrayList<String>,
                var publisher: String,
                var publishedDate: String,
                var description: String,
                var industryIdentifiers: ArrayList<IndustryIdentifiers>,
                var readingModes: ReadingModes,
                var pageCount: Int,
                var printType: String,
                var categories: ArrayList<String>,
                var averageRating: Float,
                var ratingsCount: Int,
                var maturityRating: String,
                var allowAnonLogging: Boolean,
                var contentVersion: String,
                var panelizationSummary: PanelizationSummary,
                var imageLinks: ImageLinks,
                var language: String,
                var previewLink: String,
                var infoLink: String,
                var canonicalVolumeLink: String
            ) {
                class IndustryIdentifiers(var type: String, var identifier: String)
                class ReadingModes(var text: Boolean, var image: Boolean)
                class PanelizationSummary(var text: Boolean, var image: Boolean)
                class ImageLinks(var smallThumbnail: String, var thumbnail: String)
            }

            class SaleInfo(
                var country: String,
                var saleability: String,
                var isEbook: Boolean,
                var listPrice: Price,
                var retailPrice: Price,
                var buyLink: String,
                var offers: Array<Offer>
            ) {
                class Price(var amount: Int, var currencyCode: String)
                class Offer(
                    var finskyOfferType: String,
                    var currencyCode: String,
                    var listPrice: MicrosPrice,
                    var retailPrice: MicrosPrice
                ) {
                    class MicrosPrice(var amountInMicros: Long, var currencyCode: String)
                }
            }

            class AccessInfo(
                var country: String,
                var viewability: String,
                var embeddable: Boolean,
                var publicDomain: Boolean,
                var textToSpeechPermission: String,
                var epub: Epub,
                var pdf: Pdf,
                var webReaderLink: String,
                var accessViewStatus: String,
                var quoteSharingAllowed: Boolean
            ) {
                class Epub(var isAvailable: Boolean, var acsTokenLink: String)
                class Pdf(var isAvailable: Boolean)
            }

            class SearchInfo(var textSnippet: String)
        }
    }
}




