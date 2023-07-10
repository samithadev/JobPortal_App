import android.content.Context

//@Database(entities = [Company::class], version = 1)
//
//abstract class CompanyDatabase:RoomDatabase() {
//
//    abstract fun getCompanyDao():CompanyDao
//
//    companion object {
//        @volatile
//        private var INSTANCE: CompanyDatabase? = null
//
//        fun getInstance(context: Context):CompanyDatabase{
//            synchronized(this){
//                return INSTANCE ?:Room.databaseBuilder(
//                    context.applicationContext,
//                    CompanyDatabase::class.java,
//                    "company_db"
//                ).build().also {
//                    INSTANCE = it
//                }
//            }
//        }
//    }
//}