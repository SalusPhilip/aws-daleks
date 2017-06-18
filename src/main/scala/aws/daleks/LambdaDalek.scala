package aws.daleks

import rx.lang.scala._
import scala.collection.JavaConverters._
import com.amazonaws.regions.Region
import java.util.List
import java.util.Collections
import com.amazonaws.services.lambda.invoke.LambdaFunction
import com.amazonaws.services.lambda.AWSLambdaClient
import com.amazonaws.services.lambda.model.FunctionConfiguration
import com.amazonaws.services.lambda.model.DeleteFunctionRequest

case class LambdaDalek(implicit region: Region) extends RxDalek[FunctionConfiguration] {
  val lambda = withRegion(new AWSLambdaClient)
  
  override def list() = lambda.listFunctions().getFunctions()
  
  override def mercy(ar: FunctionConfiguration) = isSparedName(ar.getFunctionName)
  
  override def exterminate(ar: FunctionConfiguration) = 
    lambda.deleteFunction(new DeleteFunctionRequest().withFunctionName(ar.getFunctionName))
  
    override def describe(ar: FunctionConfiguration) = Map(
    ("functionName"->ar.getFunctionName)    
  )
  
}